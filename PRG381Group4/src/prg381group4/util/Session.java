package prg381group4.util;

import prg381group4.model.User;

/**
 * Holds the user that is currently logged in. The desktop equivalent of an
 * HTTP session. Set on successful login, cleared on logout.
 *
 * TAMMY: role-based access checks go through requireSupervisor().
 */
public final class Session {

    private static User currentUser;

    private Session() {
    }

    public static void login(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static boolean isSupervisor() {
        return currentUser != null && currentUser.isSupervisor();
    }

    /** Guard for supervisor-only actions (delete, user management). */
    public static void requireSupervisor() throws ValidationException {
        if (!isSupervisor()) {
            throw new ValidationException("Access denied. Only a Supervisor may perform this action.");
        }
    }
}
