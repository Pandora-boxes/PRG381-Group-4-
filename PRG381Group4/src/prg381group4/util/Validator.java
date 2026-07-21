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

    private static final java.util.regex.Pattern EMAIL_PATTERN =
            java.util.regex.Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    /** Throws if the value is null, empty, or only whitespace. */
    public static void requireText(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required.");
        }
    }

    /** Throws if the value is not a valid email address. */
    public static void requireEmail(String value, String fieldName) throws ValidationException {
        requireText(value, fieldName);
        if (!EMAIL_PATTERN.matcher(value.trim()).matches()) {
            throw new ValidationException(fieldName + " must be a valid email address.");
        }
    }

    /** Throws if the text is not a whole number >= 0. Returns the parsed value. */
    public static int requireNonNegativeInt(String value, String fieldName) throws ValidationException {
        requireText(value, fieldName);
        int parsed;
        try {
            parsed = Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " must be a whole number.");
        }
        if (parsed < 0) {
            throw new ValidationException(fieldName + " cannot be negative.");
        }
        return parsed;
    }

    /** Throws if the text is not a whole number > 0. Returns the parsed value. */
    public static int requirePositiveInt(String value, String fieldName) throws ValidationException {
        int parsed = requireNonNegativeInt(value, fieldName);
        if (parsed == 0) {
            throw new ValidationException(fieldName + " must be greater than zero.");
        }
        return parsed;
    }

    /** Throws if the text is not a valid amount >= 0. Returns the parsed value. */
    public static double requireNonNegativeDouble(String value, String fieldName) throws ValidationException {
        requireText(value, fieldName);
        double parsed;
        try {
            parsed = Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " must be a number.");
        }
        if (parsed < 0) {
            throw new ValidationException(fieldName + " cannot be negative.");
        }
        return parsed;
    }

    /** Password policy: min 8 chars, at least one letter and one digit. */
    public static void requireStrongPassword(String password) throws ValidationException {
        requireText(password, "Password");
        if (password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters long.");
        }
        if (!password.chars().anyMatch(Character::isLetter)) {
            throw new ValidationException("Password must contain at least one letter.");
        }
        if (!password.chars().anyMatch(Character::isDigit)) {
            throw new ValidationException("Password must contain at least one digit.");
        }
    }
}