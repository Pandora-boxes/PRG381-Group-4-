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

    @Override
    protected User mapRow(ResultSet rs) throws SQLException {
        // TODO (Tammy)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Needed by login. Returns null when the username does not exist. */
    public User findByUsername(String username) throws DataAccessException {
        // TODO (Tammy) -- queryOne("SELECT * FROM app_user WHERE username = ?", username)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Needed by registration, to block duplicate usernames. */
    public boolean usernameExists(String username) throws DataAccessException {
        // TODO (Tammy)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Needed by registration, to block duplicate email addresses. */
    public boolean emailExists(String email) throws DataAccessException {
        // TODO (Tammy)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int insert(User u) throws DataAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<User> findAll() throws DataAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User findById(int id) throws DataAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean update(User u) throws DataAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean delete(int id) throws DataAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<User> search(String keyword) throws DataAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
