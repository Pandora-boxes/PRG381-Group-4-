package prg381group4.controller;

// OWNER: Juan
// Placeholder stub only - no logic yet.
// TODO: cleaner CRUD. Wraps CleanerDao. Copy the shape of MaterialController.
import java.util.List;
import prg381group4.model.Cleaner;
import prg381group4.model.dao.CleanerDao;
import prg381group4.util.DataAccessException;
import prg381group4.util.ValidationException;
import prg381group4.util.Validator;

public class CleanerController {

    private final CleanerDao cleanerDao = new CleanerDao();

    public List<Cleaner> getAllCleaners() throws DataAccessException {
        return cleanerDao.findAll();
    }

    public List<Cleaner> search(String keyword) throws DataAccessException {
        if (keyword == null || keyword.trim().isEmpty()) {
            return cleanerDao.findAll();
        }
        return cleanerDao.search(keyword.trim());
    }

    public int addCleaner(String employeeNo, String firstName, String lastName,
                          String email, String phone, String department)
            throws ValidationException, DataAccessException {
        Validator.requireText(employeeNo, "Employee number");
        Validator.requireText(firstName, "First name");
        Validator.requireText(lastName, "Last name");
        if (email != null && !email.trim().isEmpty()) {
            Validator.requireEmail(email, "Email");
        }

        Cleaner c = new Cleaner();
        c.setEmployeeNo(employeeNo.trim());
        c.setFirstName(firstName.trim());
        c.setLastName(lastName.trim());
        c.setEmail(email == null ? null : email.trim());
        c.setPhone(phone == null ? null : phone.trim());
        c.setDepartment(department == null ? null : department.trim());
        return cleanerDao.insert(c);
    }

    public boolean updateCleaner(int cleanerId, String employeeNo, String firstName, String lastName,
                                 String email, String phone, String department)
            throws ValidationException, DataAccessException {
        Validator.requireText(employeeNo, "Employee number");
        Validator.requireText(firstName, "First name");
        Validator.requireText(lastName, "Last name");
        if (email != null && !email.trim().isEmpty()) {
            Validator.requireEmail(email, "Email");
        }

        Cleaner c = new Cleaner();
        c.setId(cleanerId);
        c.setEmployeeNo(employeeNo.trim());
        c.setFirstName(firstName.trim());
        c.setLastName(lastName.trim());
        c.setEmail(email == null ? null : email.trim());
        c.setPhone(phone == null ? null : phone.trim());
        c.setDepartment(department == null ? null : department.trim());
        return cleanerDao.update(c);
    }

    public boolean deleteCleaner(int cleanerId) throws DataAccessException {
        return cleanerDao.delete(cleanerId);
    }
}