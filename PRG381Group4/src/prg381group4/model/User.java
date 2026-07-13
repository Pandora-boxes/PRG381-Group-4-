package prg381group4.model;

/**
 * A staff member who logs into the system.
 * Roles: STOREKEEPER (day-to-day capture) or SUPERVISOR (full access).
 */
public class User extends Person {

    public static final String ROLE_STOREKEEPER = "STOREKEEPER";
    public static final String ROLE_SUPERVISOR = "SUPERVISOR";

    private String username;
    private String passwordHash;
    private String fullName;
    private String role;

    public User() {
    }

    public User(int id, String username, String passwordHash, String fullName,
                String email, String phone, String role) {
        super(id, email, phone);
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getRoleDescription() {
        return ROLE_SUPERVISOR.equals(role) ? "Supervisor" : "Storekeeper";
    }

    /** Business rule: only supervisors may delete records and manage users. */
    public boolean isSupervisor() {
        return ROLE_SUPERVISOR.equals(role);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
