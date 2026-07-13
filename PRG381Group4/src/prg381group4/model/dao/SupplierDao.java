package prg381group4.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import prg381group4.model.Supplier;
import prg381group4.util.DataAccessException;

/**
 * JUAN: fill this in. Copy MaterialDao line for line -- same shape, simpler SQL
 * (no JOIN needed). Table: supplier(supplier_id, name, contact_person, phone,
 * email, address). Search should match on name or contact_person.
 */
public class SupplierDao extends BaseDao<Supplier> {

    @Override
    protected Supplier mapRow(ResultSet rs) throws SQLException {
        // TODO (Juan)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int insert(Supplier s) throws DataAccessException {
        // TODO (Juan) -- use executeInsert(...)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Supplier> findAll() throws DataAccessException {
        // TODO (Juan) -- use query(...)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Supplier findById(int id) throws DataAccessException {
        // TODO (Juan) -- use queryOne(...)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean update(Supplier s) throws DataAccessException {
        // TODO (Juan) -- use execute(...) > 0
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean delete(int id) throws DataAccessException {
        // TODO (Juan). Note: a supplier linked to a material CANNOT be deleted --
        // the foreign key blocks it and BaseDao turns that into a friendly message.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Supplier> search(String keyword) throws DataAccessException {
        // TODO (Juan)
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
