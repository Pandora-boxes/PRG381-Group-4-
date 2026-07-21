package prg381group4.controller;

import prg381group4.model.User;
import prg381group4.model.dao.UserDao;
import prg381group4.util.DataAccessException;
import prg381group4.util.PasswordUtil;
import prg381group4.util.Session;
import prg381group4.util.ValidationException;
import prg381group4.util.Validator;

/**
 * The only place login/registration logic lives. The view (LoginFrame,
 * a "Register" form, etc.) calls this and only this -- never UserDao directly.
 */
public class AuthController {

    private final UserDao userDao = new UserDao();

    /**
     * Verifies the username/password and, if correct, starts the session.
     *
     * @return the logged-in user
     * @throws ValidationException  if the username doesn't exist or the password is wrong
     * @throws DataAccessException  if the database call fails
     */
    public User login(String username, String password) throws ValidationException, DataAccessException {
        Validator.requireText(username, "Username");
        Validator.requireText(password, "Password");

        User user = userDao.findByUsername(username.trim());
        // Deliberately vague message: don't reveal whether it was the
        // username or the password that was wrong.
        if (user == null || !PasswordUtil.verify(password, user.getPasswordHash())) {
            throw new ValidationException("Invalid username or password.");
        }

        Session.login(user);
        return user;
    }

    /** Clears the current session. */
    public void logout() {
        Session.logout();
    }

    /**
     * Validates and registers a new staff member with a hashed password.
     * Only a supervisor may create new accounts.
     *
     * @return the generated user id
     */
    public int register(String username, String email, String password,
                         String fullName, String phone, String role)
            throws ValidationException, DataAccessException {

        Session.requireSupervisor();

        Validator.requireText(username, "Username");
        Validator.requireEmail(email, "Email");
        Validator.requireStrongPassword(password);
        Validator.requireText(fullName, "Full name");
        Validator.requireText(role, "Role");

        if (!User.ROLE_SUPERVISOR.equals(role) && !User.ROLE_STOREKEEPER.equals(role)) {
            throw new ValidationException("Role must be either Supervisor or Storekeeper.");
        }
        if (userDao.usernameExists(username.trim())) {
            throw new ValidationException("That username is already taken.");
        }
        if (userDao.emailExists(email.trim())) {
            throw new ValidationException("That email address is already registered.");
        }

        User u = new User();
        u.setUsername(username.trim());
        u.setEmail(email.trim());
        u.setPasswordHash(PasswordUtil.hash(password));
        u.setFullName(fullName.trim());
        u.setPhone(phone == null ? null : phone.trim());
        u.setRole(role);

        return userDao.insert(u);
    }
}