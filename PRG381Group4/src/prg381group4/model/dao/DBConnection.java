package prg381group4.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import prg381group4.util.DataAccessException;

/**
 * Single point of contact with the Apache Derby EMBEDDED database.
 *
 * "Embedded" means Derby runs inside our own JVM -- there is no server to
 * install or start. The database is just a folder called 'cleaninvdb' that
 * Derby creates next to the project the first time the app runs.
 */
public final class DBConnection {

    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String URL = "jdbc:derby:cleaninvdb;create=true";

    private DBConnection() {
    }

    /**
     * Opens a new connection. The caller is responsible for closing it --
     * every DAO uses try-with-resources so this happens automatically.
     */
    public static Connection getConnection() throws DataAccessException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            throw new DataAccessException(
                    "Derby driver not found. Check that derby.jar and derbyshared.jar "
                    + "are on the project classpath.", e);
        } catch (SQLException e) {
            throw new DataAccessException("Could not connect to the database: " + e.getMessage(), e);
        }
    }

    /**
     * Derby requires an explicit shutdown, otherwise the database stays locked
     * and the next run fails. Call this once when the application exits.
     * A successful shutdown always throws SQLException with state XJ015 --
     * that is Derby saying "shut down cleanly", not an error.
     */
    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            if ("XJ015".equals(e.getSQLState())) {
                System.out.println("Derby shut down normally.");
            } else {
                System.err.println("Derby shutdown problem: " + e.getMessage());
            }
        }
    }
}
