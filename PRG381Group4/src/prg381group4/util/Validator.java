package prg381group4.util;

/**
 * Central input validation. Every controller calls these BEFORE touching a DAO.
 *
 * TAMMY: this class is yours. The signatures below are the contract the rest of
 * the team codes against -- do not rename them, just fill in the bodies.
 */
public final class Validator {

    private Validator() {
    }

    /** Throws if the value is null, empty, or only whitespace. */
    public static void requireText(String value, String fieldName) throws ValidationException {
        // TODO (Tammy)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Throws if the value is not a valid email address. */
    public static void requireEmail(String value, String fieldName) throws ValidationException {
        // TODO (Tammy): simple regex, e.g. ^[\w.-]+@[\w.-]+\.[a-zA-Z]{2,}$
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Throws if the text is not a whole number >= 0. Returns the parsed value. */
    public static int requireNonNegativeInt(String value, String fieldName) throws ValidationException {
        // TODO (Tammy)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Throws if the text is not a whole number > 0. Returns the parsed value. */
    public static int requirePositiveInt(String value, String fieldName) throws ValidationException {
        // TODO (Tammy)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Throws if the text is not a valid amount >= 0. Returns the parsed value. */
    public static double requireNonNegativeDouble(String value, String fieldName) throws ValidationException {
        // TODO (Tammy)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Password policy: min 8 chars, at least one letter and one digit. */
    public static void requireStrongPassword(String password) throws ValidationException {
        // TODO (Tammy)
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
