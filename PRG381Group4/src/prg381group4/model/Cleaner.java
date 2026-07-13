package prg381group4.model;

/**
 * A cleaner that materials get issued to. Also extends Person, but builds its
 * display name from two fields instead of one -- this is the polymorphism.
 */
public class Cleaner extends Person {

    private String employeeNo;
    private String firstName;
    private String lastName;
    private String department;

    public Cleaner() {
    }

    public Cleaner(int id, String employeeNo, String firstName, String lastName,
                   String email, String phone, String department) {
        super(id, email, phone);
        this.employeeNo = employeeNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    @Override
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getRoleDescription() {
        return "Cleaner - " + (department == null ? "Unassigned" : department);
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
