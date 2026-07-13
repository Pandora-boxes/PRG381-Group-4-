package prg381group4;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import prg381group4.model.dao.DBConnection;
import prg381group4.model.dao.SchemaInitializer;
import prg381group4.util.DataAccessException;

/**
 * Application entry point.
 *
 * Boots the embedded Derby database (creating and seeding it on first run),
 * then opens the login window. Registers a shutdown hook so Derby is always
 * closed cleanly, which prevents the database being left locked.
 */
public class PRG381Group4 {

    public static void main(String[] args) {

        // Make Swing look like the host operating system instead of 1998 Java.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Falling back to the default look and feel.");
        }

        // Always shut Derby down, even if the user closes the window abruptly.
        Runtime.getRuntime().addShutdownHook(new Thread(DBConnection::shutdown));

        try {
            SchemaInitializer.initialise();
            System.out.println("Database ready.");
        } catch (DataAccessException e) {
            JOptionPane.showMessageDialog(null,
                    "The application could not start:\n\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // PANDORA: replace this block with the real login window once it exists:
        //
        //     java.awt.EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
        //
        JOptionPane.showMessageDialog(null,
                "Database is up and seeded.\n\n"
                + "Login accounts:\n"
                + "   admin / Admin@123   (Supervisor)\n"
                + "   store / Store@123   (Storekeeper)\n\n"
                + "Next step: LoginFrame.",
                "PRG381 Group 4 - Setup OK", JOptionPane.INFORMATION_MESSAGE);
    }
}
