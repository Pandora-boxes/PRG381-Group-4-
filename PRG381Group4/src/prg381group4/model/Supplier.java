package prg381group4.model;

/**
 * A company that supplies cleaning materials.
 * Not a Person -- it is an organisation, so it does not extend Person.
 */
public class Supplier {

    private int supplierId;
    private String name;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;

    public Supplier() {
    }

    public Supplier(int supplierId, String name, String contactPerson,
                    String phone, String email, String address) {
        this.supplierId = supplierId;
        this.name = name;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /** Used so the object displays nicely inside a JComboBox on the form. */
    @Override
    public String toString() {
        return name;
    }
}
