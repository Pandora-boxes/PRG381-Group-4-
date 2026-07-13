package prg381group4.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Hashes passwords with SHA-256 and a random per-user salt.
 * Plain-text passwords are NEVER stored in the database.
 *
 * Stored format:  salt:hash   (both Base64 encoded)
 */
public final class PasswordUtil {

    private PasswordUtil() {
        // utility class -- no instances
    }

    /** Hashes a plain password with a freshly generated random salt. */
    public static String hash(String plainPassword) {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        String saltB64 = Base64.getEncoder().encodeToString(salt);
        return saltB64 + ":" + sha256(saltB64 + plainPassword);
    }

    /** Re-hashes the attempt with the stored salt and compares the result. */
    public static boolean verify(String plainPassword, String storedHash) {
        if (storedHash == null || !storedHash.contains(":")) {
            return false;
        }
        String[] parts = storedHash.split(":", 2);
        String saltB64 = parts[0];
        String expected = parts[1];
        return sha256(saltB64 + plainPassword).equals(expected);
    }

    private static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            // SHA-256 and UTF-8 are guaranteed to exist in every JVM
            throw new IllegalStateException("Hashing failed", e);
        }
    }
}
