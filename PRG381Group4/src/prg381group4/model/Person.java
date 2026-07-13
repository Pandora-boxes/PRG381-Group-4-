package prg381group4.model;

/**
 * Abstract base class for every human entity in the system.
 *
 * Demonstrates ABSTRACTION (cannot be instantiated, declares abstract methods)
 * and ENCAPSULATION (protected fields exposed only through getters/setters).
 * User and Cleaner both extend this class (INHERITANCE) and provide their own
 * versions of getFullName() and getRoleDescription() (POLYMORPHISM).
 */
public abstract class Person {

    protected int id;
    protected String email;
    protected String phone;

    protected Person() {
    }

    protected Person(int id, String email, String phone) {
        this.id = id;
        this.email = email;
        this.phone = phone;
    }

    /** Each subclass builds its display name differently. */
    public abstract String getFullName();

    /** Each subclass describes its own role in the system. */
    public abstract String getRoleDescription();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return getFullName() + " (" + getRoleDescription() + ")";
    }
}
