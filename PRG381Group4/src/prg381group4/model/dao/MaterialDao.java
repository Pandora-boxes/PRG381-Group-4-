package prg381group4.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import prg381group4.model.Material;
import prg381group4.util.DataAccessException;

/**
 * REFERENCE IMPLEMENTATION -- this is the pattern for all the other DAOs.
 *
 * JUAN: copy this file's shape for SupplierDao, CleanerDao and UserDao.
 * Everything reusable already lives in BaseDao, so each DAO is only the SQL
 * plus a mapRow() method.
 */
public class MaterialDao extends BaseDao<Material> {

    /** JOIN so the table can show the supplier's name, not just its id. */
    private static final String SELECT_BASE =
            "SELECT m.*, s.name AS supplier_name "
          + "FROM material m LEFT JOIN supplier s ON m.supplier_id = s.supplier_id ";

    @Override
    protected Material mapRow(ResultSet rs) throws SQLException {
        Material m = new Material();
        m.setMaterialId(rs.getInt("material_id"));
        m.setCode(rs.getString("code"));
        m.setName(rs.getString("name"));
        m.setUnit(rs.getString("unit"));
        m.setQuantity(rs.getInt("quantity"));
        m.setReorderLevel(rs.getInt("reorder_level"));
        m.setUnitPrice(rs.getDouble("unit_price"));
        m.setSupplierId(rs.getInt("supplier_id"));
        m.setSupplierName(rs.getString("supplier_name"));
        return m;
    }

    @Override
    public int insert(Material m) throws DataAccessException {
        String sql = "INSERT INTO material (code, name, unit, quantity, reorder_level, unit_price, supplier_id) "
                   + "VALUES (?,?,?,?,?,?,?)";
        return executeInsert(sql, m.getCode(), m.getName(), m.getUnit(), m.getQuantity(),
                m.getReorderLevel(), m.getUnitPrice(), m.getSupplierId());
    }

    @Override
    public List<Material> findAll() throws DataAccessException {
        return query(SELECT_BASE + "ORDER BY m.name");
    }

    @Override
    public Material findById(int id) throws DataAccessException {
        return queryOne(SELECT_BASE + "WHERE m.material_id = ?", id);
    }

    @Override
    public boolean update(Material m) throws DataAccessException {
        String sql = "UPDATE material SET code=?, name=?, unit=?, quantity=?, "
                   + "reorder_level=?, unit_price=?, supplier_id=? WHERE material_id=?";
        return execute(sql, m.getCode(), m.getName(), m.getUnit(), m.getQuantity(),
                m.getReorderLevel(), m.getUnitPrice(), m.getSupplierId(), m.getMaterialId()) > 0;
    }

    @Override
    public boolean delete(int id) throws DataAccessException {
        return execute("DELETE FROM material WHERE material_id = ?", id) > 0;
    }

    @Override
    public List<Material> search(String keyword) throws DataAccessException {
        String like = "%" + keyword.toLowerCase() + "%";
        return query(SELECT_BASE
                + "WHERE LOWER(m.name) LIKE ? OR LOWER(m.code) LIKE ? ORDER BY m.name",
                like, like);
    }

    /** Every item at or below its reorder level -- drives the dashboard alert. */
    public List<Material> findLowStock() throws DataAccessException {
        return query(SELECT_BASE + "WHERE m.quantity <= m.reorder_level ORDER BY m.quantity");
    }
}
