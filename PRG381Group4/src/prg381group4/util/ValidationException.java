package prg381group4.util;

/**
 * Thrown when user input breaks a business rule (empty field, bad email,
 * duplicate username, negative quantity, ...).
 * The forms catch this and show the message in a JOptionPane.
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}
