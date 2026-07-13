package prg381group4.model.dao;

import java.util.List;
import prg381group4.util.DataAccessException;

/**
 * The CRUD contract that every DAO must honour.
 *
 * This is ABSTRACTION: the controllers depend on this interface, not on any
 * specific DAO class. <T> is the entity type (Material, Supplier, Cleaner...).
 *
 * @param <T> the model class this DAO reads and writes
 */
public interface Dao<T> {

    /** CREATE -- returns the generated primary key. */
    int insert(T entity) throws DataAccessException;

    /** READ -- every row. */
    List<T> findAll() throws DataAccessException;

    /** READ -- one row, or null if there is no match. */
    T findById(int id) throws DataAccessException;

    /** UPDATE -- returns true if a row was changed. */
    boolean update(T entity) throws DataAccessException;

    /** DELETE -- returns true if a row was removed. */
    boolean delete(int id) throws DataAccessException;

    /** Search / filter on the entity's main text fields. */
    List<T> search(String keyword) throws DataAccessException;
}
