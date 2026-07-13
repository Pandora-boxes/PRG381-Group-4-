package prg381group4.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import prg381group4.util.DataAccessException;
import prg381group4.util.PasswordUtil;

/**
 * Creates the five tables and loads demo data the first time the app runs.
 * Safe to call on every startup -- it detects tables that already exist.
 *
 * Derby has NO "CREATE TABLE IF NOT EXISTS". The standard workaround is to run
 * the CREATE and swallow SQLState X0Y32, which means "object already exists".
 */
public final class SchemaInitializer {

    private static final String TABLE_ALREADY_EXISTS = "X0Y32";

    private SchemaInitializer() {
    }

    public static void initialise() throws DataAccessException {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {

            for (String ddl : TABLES) {
                createTable(st, ddl);
            }
            seedIfEmpty(conn);

        } catch (SQLException e) {
            throw new DataAccessException("Database initialisation failed: " + e.getMessage(), e);
        }
    }

    private static void createTable(Statement st, String ddl) throws SQLException {
        try {
            st.executeUpdate(ddl);
            System.out.println("Created table.");
        } catch (SQLException e) {
            if (!TABLE_ALREADY_EXISTS.equals(e.getSQLState())) {
                throw e;   // a real error -- let it bubble up
            }
            // table was already there: nothing to do
        }
    }

    private static final String[] TABLES = {

        "CREATE TABLE app_user ("
        + " user_id       INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
        + " username      VARCHAR(50)  NOT NULL UNIQUE,"
        + " email         VARCHAR(100) NOT NULL UNIQUE,"
        + " password_hash VARCHAR(200) NOT NULL,"
        + " full_name     VARCHAR(100) NOT NULL,"
        + " phone         VARCHAR(20),"
        + " role          VARCHAR(20)  NOT NULL,"
        + " created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,"
        + " CONSTRAINT chk_user_role CHECK (role IN ('STOREKEEPER','SUPERVISOR'))"
        + ")",

        "CREATE TABLE supplier ("
        + " supplier_id    INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
        + " name           VARCHAR(100) NOT NULL UNIQUE,"
        + " contact_person VARCHAR(100),"
        + " phone          VARCHAR(20),"
        + " email          VARCHAR(100) UNIQUE,"
        + " address        VARCHAR(200)"
        + ")",

        "CREATE TABLE material ("
        + " material_id   INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
        + " code          VARCHAR(20)  NOT NULL UNIQUE,"
        + " name          VARCHAR(100) NOT NULL,"
        + " unit          VARCHAR(20)  NOT NULL,"
        + " quantity      INTEGER      NOT NULL DEFAULT 0,"
        + " reorder_level INTEGER      NOT NULL DEFAULT 0,"
        + " unit_price    DECIMAL(10,2) NOT NULL DEFAULT 0.00,"
        + " supplier_id   INTEGER,"
        + " CONSTRAINT chk_material_qty     CHECK (quantity >= 0),"
        + " CONSTRAINT chk_material_reorder CHECK (reorder_level >= 0),"
        + " CONSTRAINT chk_material_price   CHECK (unit_price >= 0),"
        + " CONSTRAINT fk_material_supplier FOREIGN KEY (supplier_id)"
        + "     REFERENCES supplier(supplier_id)"
        + ")",

        "CREATE TABLE cleaner ("
        + " cleaner_id  INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
        + " employee_no VARCHAR(20) NOT NULL UNIQUE,"
        + " first_name  VARCHAR(50) NOT NULL,"
        + " last_name   VARCHAR(50) NOT NULL,"
        + " email       VARCHAR(100),"
        + " phone       VARCHAR(20),"
        + " department  VARCHAR(50)"
        + ")",

        "CREATE TABLE issuance ("
        + " issuance_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
        + " material_id INTEGER NOT NULL,"
        + " cleaner_id  INTEGER NOT NULL,"
        + " issued_by   INTEGER NOT NULL,"
        + " quantity    INTEGER NOT NULL,"
        + " issue_date  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
        + " notes       VARCHAR(200),"
        + " CONSTRAINT chk_issuance_qty CHECK (quantity > 0),"
        + " CONSTRAINT fk_issuance_material FOREIGN KEY (material_id)"
        + "     REFERENCES material(material_id),"
        + " CONSTRAINT fk_issuance_cleaner  FOREIGN KEY (cleaner_id)"
        + "     REFERENCES cleaner(cleaner_id),"
        + " CONSTRAINT fk_issuance_user     FOREIGN KEY (issued_by)"
        + "     REFERENCES app_user(user_id)"
        + ")"
    };

    /** Loads demo data, but only if the database is still empty. */
    private static void seedIfEmpty(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM app_user")) {
            rs.next();
            if (rs.getInt(1) > 0) {
                return;   // already seeded
            }
        }

        System.out.println("Empty database -- loading demo data...");

        insertUser(conn, "admin", "admin@bc.ac.za", "Admin@123", "Thabo Nkosi", "SUPERVISOR");
        insertUser(conn, "store", "store@bc.ac.za", "Store@123", "Lerato Dlamini", "STOREKEEPER");

        insertSupplier(conn, "CleanCo Supplies", "Sipho Khumalo", "012 345 6789", "sales@cleanco.co.za", "12 Main Rd, Pretoria");
        insertSupplier(conn, "HygienePro", "Anna Botha", "011 222 3344", "info@hygienepro.co.za", "5 Park Ave, Centurion");

        // supplier ids are 1 and 2 because the table was empty
        insertMaterial(conn, "MAT001", "Bleach 5L",        "bottle", 40,  10, 89.99, 1);
        insertMaterial(conn, "MAT002", "Floor Mop",        "unit",    8,  10, 54.50, 1);  // low stock
        insertMaterial(conn, "MAT003", "Disposable Gloves","box",    25,  15, 120.00, 2);
        insertMaterial(conn, "MAT004", "Toilet Paper",     "pack",    5,  20, 210.00, 2);  // low stock
        insertMaterial(conn, "MAT005", "Glass Cleaner 1L", "bottle", 60,  15, 45.00, 1);

        insertCleaner(conn, "EMP101", "Nomsa",  "Mahlangu", "nomsa@bc.ac.za",  "072 111 2222", "Residences");
        insertCleaner(conn, "EMP102", "Pieter", "van Wyk",  "pieter@bc.ac.za", "073 333 4444", "Lecture Halls");
        insertCleaner(conn, "EMP103", "Fatima", "Adams",    "fatima@bc.ac.za", "074 555 6666", "Admin Block");

        System.out.println("Demo data loaded. Login with admin / Admin@123");
    }

    private static void insertUser(Connection c, String username, String email,
                                   String plainPassword, String fullName, String role) throws SQLException {
        String sql = "INSERT INTO app_user (username, email, password_hash, full_name, role) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, PasswordUtil.hash(plainPassword));
            ps.setString(4, fullName);
            ps.setString(5, role);
            ps.executeUpdate();
        }
    }

    private static void insertSupplier(Connection c, String name, String contact,
                                       String phone, String email, String address) throws SQLException {
        String sql = "INSERT INTO supplier (name, contact_person, phone, email, address) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, contact);
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.setString(5, address);
            ps.executeUpdate();
        }
    }

    private static void insertMaterial(Connection c, String code, String name, String unit,
                                       int qty, int reorder, double price, int supplierId) throws SQLException {
        String sql = "INSERT INTO material (code, name, unit, quantity, reorder_level, unit_price, supplier_id)"
                   + " VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.setString(2, name);
            ps.setString(3, unit);
            ps.setInt(4, qty);
            ps.setInt(5, reorder);
            ps.setDouble(6, price);
            ps.setInt(7, supplierId);
            ps.executeUpdate();
        }
    }

    private static void insertCleaner(Connection c, String empNo, String first, String last,
                                      String email, String phone, String dept) throws SQLException {
        String sql = "INSERT INTO cleaner (employee_no, first_name, last_name, email, phone, department)"
                   + " VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, empNo);
            ps.setString(2, first);
            ps.setString(3, last);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, dept);
            ps.executeUpdate();
        }
    }
}
