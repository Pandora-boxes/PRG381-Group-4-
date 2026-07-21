package prg381group4.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import prg381group4.model.User;
import prg381group4.util.DataAccessException;

/**
 * TAMMY: this one is yours, because AuthController depends on it.
 * Table: app_user(user_id, username, email, password_hash, full_name, phone, role).
 *
 * IMPORTANT: never select or store a plain password. AuthController hashes with
 * PasswordUtil before it ever reaches this class.
 */
public class UserDao extends BaseDao<User> {

    private static final String SELECT_BASE = "SELECT * FROM app_user ";

    @Override
    protected User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("user_id"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setFullName(rs.getString("full_name"));
        u.setPhone(rs.getString("phone"));
        u.setRole(rs.getString("role"));
        return u;
    }

    /** Needed by login. Returns null when the username does not exist. */
    public User findByUsername(String username) throws DataAccessException {
        return queryOne(SELECT_BASE + "WHERE username = ?", username);
    }

    /** Needed by registration, to block duplicate usernames. */
    public boolean usernameExists(String username) throws DataAccessException {
        return findByUsername(username) != null;
    }

    /** Needed by registration, to block duplicate email addresses. */
    public boolean emailExists(String email) throws DataAccessException {
        return queryOne(SELECT_BASE + "WHERE email = ?", email) != null;
    }

    @Override
    public int insert(User u) throws DataAccessException {
        String sql = "INSERT INTO app_user (username, email, password_hash, full_name, phone, role) "
                   + "VALUES (?,?,?,?,?,?)";
        return executeInsert(sql, u.getUsername(), u.getEmail(), u.getPasswordHash(),
                u.getFullName(), u.getPhone(), u.getRole());
    }

    @Override
    public List<User> findAll() throws DataAccessException {
        return query(SELECT_BASE + "ORDER BY username");
    }

    @Override
    public User findById(int id) throws DataAccessException {
        return queryOne(SELECT_BASE + "WHERE user_id = ?", id);
    }

    @Override
    public boolean update(User u) throws DataAccessException {
        String sql = "UPDATE app_user SET email=?, full_name=?, phone=?, role=? WHERE user_id=?";
        return execute(sql, u.getEmail(), u.getFullName(), u.getPhone(), u.getRole(), u.getId()) > 0;
    }

    @Override
    public boolean delete(int id) throws DataAccessException {
        return execute("DELETE FROM app_user WHERE user_id = ?", id) > 0;
    }

    @Override
    public List<User> search(String keyword) throws DataAccessException {
        String like = "%" + keyword.toLowerCase() + "%";
        return query(SELECT_BASE
                + "WHERE LOWER(username) LIKE ? OR LOWER(full_name) LIKE ? OR LOWER(email) LIKE ? "
                + "ORDER BY username", like, like, like);
    }
}
