package prg381group4.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import prg381group4.model.Issuance;
import prg381group4.util.DataAccessException;
import prg381group4.util.InsufficientStockException;

/**
 * REFERENCE IMPLEMENTATION of the one piece of real business logic in the
 * system: issuing stock. issue() is written out in full because a transaction
 * is easy to get subtly wrong -- but you must be able to EXPLAIN it in the
 * demo, so read it until you can.
 */
public class IssuanceDao extends BaseDao<Issuance> {

    private static final String SELECT_BASE =
            "SELECT i.*, m.name AS material_name, "
          + "       c.first_name || ' ' || c.last_name AS cleaner_name, "
          + "       u.full_name AS issued_by_name "
          + "FROM issuance i "
          + "JOIN material m ON i.material_id = m.material_id "
          + "JOIN cleaner  c ON i.cleaner_id  = c.cleaner_id "
          + "JOIN app_user u ON i.issued_by   = u.user_id ";

    @Override
    protected Issuance mapRow(ResultSet rs) throws SQLException {
        Issuance i = new Issuance();
        i.setIssuanceId(rs.getInt("issuance_id"));
        i.setMaterialId(rs.getInt("material_id"));
        i.setCleanerId(rs.getInt("cleaner_id"));
        i.setIssuedBy(rs.getInt("issued_by"));
        i.setQuantity(rs.getInt("quantity"));
        i.setIssueDate(rs.getTimestamp("issue_date"));
        i.setNotes(rs.getString("notes"));
        i.setMaterialName(rs.getString("material_name"));
        i.setCleanerName(rs.getString("cleaner_name"));
        i.setIssuedByName(rs.getString("issued_by_name"));
        return i;
    }

    /**
     * Issues stock to a cleaner AS A SINGLE TRANSACTION.
     *
     * Three things must happen together or not at all:
     *   1. check that enough stock exists (and lock the row while we look),
     *   2. write the issuance record,
     *   3. deduct the quantity from the material.
     *
     * If step 3 failed after step 2 succeeded, we would have handed out stock
     * that the system still thinks is on the shelf. setAutoCommit(false) plus
     * rollback() is what makes that impossible.
     */
    public int issue(Issuance issuance) throws DataAccessException, InsufficientStockException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);          // ---- transaction starts here ----

            // 1. read current stock
            String checkSql = "SELECT name, quantity FROM material WHERE material_id = ?";
            String materialName;
            int available;
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setInt(1, issuance.getMaterialId());
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        throw new DataAccessException("That material no longer exists.");
                    }
                    materialName = rs.getString("name");
                    available = rs.getInt("quantity");
                }
            }

            // 2. business rule: never issue more than is on hand
            if (issuance.getQuantity() > available) {
                conn.rollback();
                throw new InsufficientStockException(materialName, issuance.getQuantity(), available);
            }

            // 3. write the issuance record
            int newId;
            String insertSql = "INSERT INTO issuance (material_id, cleaner_id, issued_by, quantity, notes) "
                             + "VALUES (?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, issuance.getMaterialId());
                ps.setInt(2, issuance.getCleanerId());
                ps.setInt(3, issuance.getIssuedBy());
                ps.setInt(4, issuance.getQuantity());
                ps.setString(5, issuance.getNotes());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    newId = keys.next() ? keys.getInt(1) : -1;
                }
            }

            // 4. deduct the stock
            String deductSql = "UPDATE material SET quantity = quantity - ? WHERE material_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deductSql)) {
                ps.setInt(1, issuance.getQuantity());
                ps.setInt(2, issuance.getMaterialId());
                ps.executeUpdate();
            }

            conn.commit();                      // ---- both writes land together ----
            return newId;

        } catch (SQLException e) {
            rollbackQuietly(conn);
            throw new DataAccessException("Issuing stock failed: " + e.getMessage(), e);
        } finally {
            closeQuietly(conn);
        }
    }

    private void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
                // nothing useful we can do here
            }
        }
    }

    private void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }

    // ---------------- reads used by the dashboard and the reports ----------------

    @Override
    public List<Issuance> findAll() throws DataAccessException {
        return query(SELECT_BASE + "ORDER BY i.issue_date DESC");
    }

    /** Derby has no LIMIT -- the SQL standard spelling is FETCH FIRST n ROWS ONLY. */
    public List<Issuance> findRecent(int howMany) throws DataAccessException {
        return query(SELECT_BASE + "ORDER BY i.issue_date DESC FETCH FIRST " + howMany + " ROWS ONLY");
    }

    @Override
    public Issuance findById(int id) throws DataAccessException {
        return queryOne(SELECT_BASE + "WHERE i.issuance_id = ?", id);
    }

    @Override
    public List<Issuance> search(String keyword) throws DataAccessException {
        String like = "%" + keyword.toLowerCase() + "%";
        return query(SELECT_BASE
                + "WHERE LOWER(m.name) LIKE ? OR LOWER(c.first_name) LIKE ? OR LOWER(c.last_name) LIKE ? "
                + "ORDER BY i.issue_date DESC",
                like, like, like);
    }

    /**
     * JUAN: an issuance is a historical record, so we do NOT allow editing it.
     * Leave insert() and update() unsupported on purpose and say so in the demo --
     * "we treat issuances as an append-only audit trail" is a good answer.
     */
    @Override
    public int insert(Issuance i) throws DataAccessException {
        throw new UnsupportedOperationException("Use issue() -- stock must be deducted in the same transaction.");
    }

    @Override
    public boolean update(Issuance i) throws DataAccessException {
        throw new UnsupportedOperationException("Issuance history is append-only.");
    }

    /** Deleting an issuance must give the stock back. TODO (Juan): supervisor only. */
   @Override
    public boolean delete(int id) throws DataAccessException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            int materialId;
            int quantity;
            String checkSql = "SELECT material_id, quantity FROM issuance WHERE issuance_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }
                    materialId = rs.getInt("material_id");
                    quantity = rs.getInt("quantity");
                }
            }

            int rows;
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM issuance WHERE issuance_id = ?")) {
                ps.setInt(1, id);
                rows = ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE material SET quantity = quantity + ? WHERE material_id = ?")) {
                ps.setInt(1, quantity);
                ps.setInt(2, materialId);
                ps.executeUpdate();
            }

            conn.commit();
            return rows > 0;

        } catch (SQLException e) {
            rollbackQuietly(conn);
            throw new DataAccessException("Deleting issuance failed: " + e.getMessage(), e);
        } finally {
            closeQuietly(conn);
        }
    }
}
