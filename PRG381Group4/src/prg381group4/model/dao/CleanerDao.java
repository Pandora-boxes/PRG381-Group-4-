package prg381group4.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import prg381group4.model.Cleaner;
import prg381group4.util.DataAccessException;

/**
 * JUAN: fill this in, same pattern as MaterialDao.
 * Table: cleaner(cleaner_id, employee_no, first_name, last_name, email, phone, department).
 * Search should match on first_name, last_name or employee_no.
 */
public class CleanerDao extends BaseDao<Cleaner> {

    @Override
    protected Cleaner mapRow(ResultSet rs) throws SQLException {
        // TODO (Juan)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int insert(Cleaner c) throws DataAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Cleaner> findAll() throws DataAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Cleaner findById(int id) throws DataAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean update(Cleaner c) throws DataAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean delete(int id) throws DataAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Cleaner> search(String keyword) throws DataAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
