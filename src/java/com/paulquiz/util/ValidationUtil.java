package com.paulquiz.util;

import java.util.regex.Pattern;

/**
 * Utility class for input validation
 */
public class ValidationUtil {

    // Email regex pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    /**
     * Validate email format
     * 
     * @param email The email to validate
     * @return true if email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validate that string is not null or empty
     * 
     * @param str The string to validate
     * @return true if string is valid (not null and not empty), false otherwise
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Validate string length
     * 
     * @param str       The string to validate
     * @param minLength Minimum length
     * @param maxLength Maximum length
     * @return true if length is within range, false otherwise
     */
    public static boolean isValidLength(String str, int minLength, int maxLength) {
        if (str == null)
            return false;
        int length = str.length();
        return length >= minLength && length <= maxLength;
    }

    /**
     * Validate numeric string
     * 
     * @param str The string to validate
     * @return true if string is numeric, false otherwise
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * Validate integer value
     * 
     * @param str The string to validate and parse
     * @return Integer value if valid, null otherwise
     */
    public static Integer parseInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Validate long value
     * 
     * @param str The string to validate and parse
     * @return Long value if valid, null otherwise
     */
    public static Long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Sanitize string input (prevent XSS)
     * 
     * @param input The input to sanitize
     * @return Sanitized string
     */
    public static String sanitizeInput(String input) {
        if (input == null)
            return null;

        return input
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;")
                .replace("/", "&#x2F;");
    }

    /**
     * Validate that value is within range
     * 
     * @param value The value to check
     * @param min   Minimum value (inclusive)
     * @param max   Maximum value (inclusive)
     * @return true if value is within range, false otherwise
     */
    public static boolean isWithinRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * Validate quiz score (0-100)
     * 
     * @param score The score to validate
     * @return true if score is valid (0-100), false otherwise
     */
    public static boolean isValidScore(Integer score) {
        return score != null && isWithinRange(score, 0, 100);
    }

    /**
     * Memvalidasi format username (hanya huruf, angka, dan underscore)
     * Minimal 3 karakter, maksimal 20 karakter
     * @param username Username yang dicek
     * @return true jika valid
     */
    public static boolean isValidUsername(String username) {
        if (username == null) return false;
        return username.matches("^[a-zA-Z0-9_]{3,20}$");
    }

    /**
     * Menghapus semua tag HTML secara total (Deep Clean)
     * Berguna untuk input yang benar-benar tidak boleh ada HTML
     * @param input Input kotor
     * @return String bersih tanpa tag
     */
    public static String stripHtml(String input) {
        if (input == null) return null;
        return input.replaceAll("<[^>]*>", "");
    }
}

