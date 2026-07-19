package prg381group4.model.dao;


/**
 * JUAN: fill this in, same pattern as MaterialDao.
 * Table: cleaner(cleaner_id, employee_no, first_name, last_name, email, phone, department).
 * Search should match on first_name, last_name or employee_no.
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import prg381group4.model.Cleaner;
import prg381group4.util.DataAccessException;

public class CleanerDao extends BaseDao<Cleaner> {

    private static final String SELECT_BASE = "SELECT * FROM cleaner ";

    @Override
    protected Cleaner mapRow(ResultSet rs) throws SQLException {
        Cleaner c = new Cleaner();
        c.setId(rs.getInt("cleaner_id"));
        c.setEmployeeNo(rs.getString("employee_no"));
        c.setFirstName(rs.getString("first_name"));
        c.setLastName(rs.getString("last_name"));
        c.setEmail(rs.getString("email"));
        c.setPhone(rs.getString("phone"));
        c.setDepartment(rs.getString("department"));
        return c;
    }

    @Override
    public int insert(Cleaner c) throws DataAccessException {
        String sql = "INSERT INTO cleaner (employee_no, first_name, last_name, email, phone, department) "
                   + "VALUES (?,?,?,?,?,?)";
        return executeInsert(sql, c.getEmployeeNo(), c.getFirstName(), c.getLastName(),
                c.getEmail(), c.getPhone(), c.getDepartment());
    }

    @Override
    public List<Cleaner> findAll() throws DataAccessException {
        return query(SELECT_BASE + "ORDER BY last_name, first_name");
    }

    @Override
    public Cleaner findById(int id) throws DataAccessException {
        return queryOne(SELECT_BASE + "WHERE cleaner_id = ?", id);
    }

    @Override
    public boolean update(Cleaner c) throws DataAccessException {
        String sql = "UPDATE cleaner SET employee_no=?, first_name=?, last_name=?, email=?, phone=?, department=? "
                   + "WHERE cleaner_id=?";
        return execute(sql, c.getEmployeeNo(), c.getFirstName(), c.getLastName(),
                c.getEmail(), c.getPhone(), c.getDepartment(), c.getId()) > 0;
    }

    @Override
    public boolean delete(int id) throws DataAccessException {
        return execute("DELETE FROM cleaner WHERE cleaner_id = ?", id) > 0;
    }

    @Override
    public List<Cleaner> search(String keyword) throws DataAccessException {
        String like = "%" + keyword.toLowerCase() + "%";
        return query(SELECT_BASE
                + "WHERE LOWER(first_name) LIKE ? OR LOWER(last_name) LIKE ? OR LOWER(employee_no) LIKE ? "
                + "ORDER BY last_name, first_name",
                like, like, like);
    }
}