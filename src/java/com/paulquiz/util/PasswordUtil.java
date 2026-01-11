package com.paulquiz.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password hashing and verification using BCrypt
 */
public class PasswordUtil {

    // BCrypt workload (number of hashing rounds), 10 is recommended
    private static final int WORKLOAD = 10;

    /**
     * Hash a password using BCrypt
     * 
     * @param plaintextPassword The plain text password to hash
     * @return Hashed password string
     */
    public static String hashPassword(String plaintextPassword) {
        if (plaintextPassword == null || plaintextPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Generate salt and hash the password
        String salt = BCrypt.gensalt(WORKLOAD);
        return BCrypt.hashpw(plaintextPassword, salt);
    }

    /**
     * Verify a plain text password against a hashed password
     * 
     * @param plaintextPassword The plain text password to verify
     * @param hashedPassword    The hashed password to compare against
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String plaintextPassword, String hashedPassword) {
        if (plaintextPassword == null || hashedPassword == null) {
            return false;
        }

        try {
            return BCrypt.checkpw(plaintextPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            // Invalid hash format
            return false;
        }
    }

    /**
     * Check if password meets minimum security requirements
     * 
     * @param password The password to check
     * @return true if password is strong enough, false otherwise
     */
    public static boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        // Check for at least one letter and one number
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");

        return hasLetter && hasDigit;
    }

    /**
     * Get password strength message
     * 
     * @param password The password to check
     * @return String message describing password strength
     */
    public static String getPasswordStrengthMessage(String password) {
        if (password == null || password.isEmpty()) {
            return "Password tidak boleh kosong";
        }

        if (password.length() < 8) {
            return "Password minimal 8 karakter";
        }

        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");

        if (!hasLetter) {
            return "Password harus mengandung huruf";
        }

        if (!hasDigit) {
            return "Password harus mengandung angka";
        }

        return "Password kuat";
    }

    /**
     * Mengecek apakah password mengandung karakter spesial (biar makin aman)
     * @param password Password yang dicek
     * @return true jika ada simbol seperti @#$%^&
     */
    public static boolean hasSpecialChar(String password) {
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }

    /**
     * Membersihkan input password dari spasi di awal/akhir 
     * untuk mencegah error typo yang tidak terlihat
     * @param password Password kotor
     * @return Password bersih
     */
    public static String sanitize(String password) {
        return (password == null) ? "" : password.trim();
    }

    /**
     * Menghasilkan hint sensor password (misal: p****d)
     * Berguna untuk halaman profil agar password asli tidak terlihat
     * @param password Password asli
     * @return String tersensor
     */
    public static String maskPassword(String password) {
        if (password == null || password.length() < 2) return "***";
        return password.charAt(0) + "****" + password.charAt(password.length() - 1);
    }
}

