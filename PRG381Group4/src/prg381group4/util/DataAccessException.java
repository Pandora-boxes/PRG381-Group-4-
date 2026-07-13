package prg381group4.util;

/**
 * Wraps any SQLException coming out of the DAO layer so that the view and
 * controller never need to import java.sql. Checked on purpose -- callers are
 * forced to handle it, which is what earns the exception-handling marks.
 */
public class DataAccessException extends Exception {

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
