package prg381group4.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import prg381group4.util.DataAccessException;

/**
 * Shared plumbing for every DAO. Holds the code that would otherwise be
 * copy-pasted five times: opening a connection, running a query, looping the
 * ResultSet, closing everything, turning SQLException into DataAccessException.
 *
 * INHERITANCE + POLYMORPHISM: each subclass only supplies mapRow(), and this
 * class calls it without knowing or caring which entity it builds.
 *
 * @param <T> the model class this DAO reads and writes
 */
public abstract class BaseDao<T> implements Dao<T> {

    /** Builds one entity object out of the current ResultSet row. */
    protected abstract T mapRow(ResultSet rs) throws SQLException;

    /** Runs a SELECT and maps every row into a list. */
    protected List<T> query(String sql, Object... params) throws DataAccessException {
        List<T> results = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            bind(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));   // <-- the subclass decides what to build
                }
            }
            return results;

        } catch (SQLException e) {
            throw new DataAccessException("Query failed: " + e.getMessage(), e);
        }
    }

    /** Runs a SELECT expected to return at most one row. */
    protected T queryOne(String sql, Object... params) throws DataAccessException {
        List<T> results = query(sql, params);
        return results.isEmpty() ? null : results.get(0);
    }

    /** Runs an INSERT/UPDATE/DELETE. Returns the number of rows affected. */
    protected int execute(String sql, Object... params) throws DataAccessException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            bind(ps, params);
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException(friendly(e), e);
        }
    }

    /** Runs an INSERT and returns the auto-generated primary key. */
    protected int executeInsert(String sql, Object... params) throws DataAccessException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            bind(ps, params);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }

        } catch (SQLException e) {
            throw new DataAccessException(friendly(e), e);
        }
    }

    /** Fills the ? placeholders in order. */
    private void bind(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }

    /**
     * Turns Derby's cryptic SQL states into a message a user can act on.
     * 23505 = unique constraint broken, 23513 = CHECK constraint broken,
     * 23503 = foreign key still referenced.
     */
    private String friendly(SQLException e) {
        switch (e.getSQLState()) {
            case "23505":
                return "That value already exists. Duplicates are not allowed.";
            case "23513":
                return "The value breaks a business rule (for example, stock cannot go negative).";
            case "23503":
                return "This record is still in use by another record and cannot be deleted.";
            default:
                return "Database error: " + e.getMessage();
        }
    }
}
