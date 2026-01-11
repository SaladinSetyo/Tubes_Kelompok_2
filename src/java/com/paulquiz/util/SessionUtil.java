package com.paulquiz.util;

import java.io.IOException;

import com.paulquiz.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Utility class for HTTP session management
 */
public class SessionUtil {

    // Session attribute keys
    private static final String USER_KEY = "user";
    private static final String USER_ID_KEY = "userId";
    private static final String FLASH_MESSAGE_KEY = "flashMessage";
    private static final String FLASH_TYPE_KEY = "flashType";
    private static final String ERROR_MESSAGE_KEY = "errorMessage";

    /**
     * Store user in session (login)
     * 
     * @param session The HTTP session
     * @param user    The user object to store
     */
    public static void login(HttpSession session, User user) {
        if (session != null && user != null) {
            session.setAttribute(USER_KEY, user);
            session.setAttribute(USER_ID_KEY, user.getId());
            // Remove any temporary session data
            session.removeAttribute(ERROR_MESSAGE_KEY);
        }
    }

    /**
     * Remove user from session (logout)
     * 
     * @param session The HTTP session
     */
    public static void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * Check if user is logged in
     * 
     * @param session The HTTP session
     * @return true if user is logged in, false otherwise
     */
    public static boolean isLoggedIn(HttpSession session) {
        if (session == null)
            return false;
        return session.getAttribute(USER_KEY) != null;
    }

    /**
     * Get current logged in user
     * 
     * @param session The HTTP session
     * @return User object if logged in, null otherwise
     */
    public static User getCurrentUser(HttpSession session) {
        if (session == null)
            return null;
        return (User) session.getAttribute(USER_KEY);
    }

    /**
     * Get current user ID
     * 
     * @param session The HTTP session
     * @return User ID if logged in, null otherwise
     */
    public static Long getCurrentUserId(HttpSession session) {
        if (session == null)
            return null;
        return (Long) session.getAttribute(USER_ID_KEY);
    }

    /**
     * Check if current user is admin
     * 
     * @param session The HTTP session
     * @return true if user is admin, false otherwise
     */
    public static boolean isAdmin(HttpSession session) {
        User user = getCurrentUser(session);
        return user != null && user.isAdmin();
    }

    /**
     * Set flash message (temporary message for next request)
     * 
     * @param session The HTTP session
     * @param message The message text
     * @param type    The message type (success, error, warning, info)
     */
    public static void setFlashMessage(HttpSession session, String message, String type) {
        if (session != null) {
            session.setAttribute(FLASH_MESSAGE_KEY, message);
            session.setAttribute(FLASH_TYPE_KEY, type);
        }
    }

    /**
     * Get and remove flash message (one-time message)
     * 
     * @param session The HTTP session
     * @return Flash message string, or null if none
     */
    public static String getFlashMessage(HttpSession session) {
        if (session == null)
            return null;
        String message = (String) session.getAttribute(FLASH_MESSAGE_KEY);
        session.removeAttribute(FLASH_MESSAGE_KEY);
        return message;
    }

    /**
     * Get and remove flash message type
     * 
     * @param session The HTTP session
     * @return Flash message type, or null if none
     */
    public static String getFlashType(HttpSession session) {
        if (session == null)
            return null;
        String type = (String) session.getAttribute(FLASH_TYPE_KEY);
        session.removeAttribute(FLASH_TYPE_KEY);
        return type;
    }

    /**
     * Set error message
     * 
     * @param session The HTTP session
     * @param message The error message
     */
    public static void setErrorMessage(HttpSession session, String message) {
        setFlashMessage(session, message, "error");
    }

    /**
     * Set success message
     * 
     * @param session The HTTP session
     * @param message The success message
     */
    public static void setSuccessMessage(HttpSession session, String message) {
        setFlashMessage(session, message, "success");
    }

    /**
     * Set warning message
     * 
     * @param session The HTTP session
     * @param message The warning message
     */
    public static void setWarningMessage(HttpSession session, String message) {
        setFlashMessage(session, message, "warning");
    }

    /**
     * Set info message
     * 
     * @param session The HTTP session
     * @param message The info message
     */
    public static void setInfoMessage(HttpSession session, String message) {
        setFlashMessage(session, message, "info");
    }

    /**
     * Get intended URL (for redirect after login)
     * 
     * @param request The HTTP request
     * @return Intended URL or default URL
     */
    public static String getIntendedUrl(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String intendedUrl = (String) session.getAttribute("intendedUrl");
            if (intendedUrl != null) {
                session.removeAttribute("intendedUrl");
                return intendedUrl;
            }
        }
        return request.getContextPath() + "/";
    }

    /**
     * Set intended URL (where user was trying to go before logging in)
     * 
     * @param session The HTTP session
     * @param url     The URL to remember
     */
    public static void setIntendedUrl(HttpSession session, String url) {
        if (session != null && url != null) {
            session.setAttribute("intendedUrl", url);
        }
    }

    /**
     * Require admin access - redirect to login if not admin
     * 
     * @param session  HttpSession
     * @param response HttpServletResponse
     * @return true if user is admin, false if redirected
     * @throws IOException if redirect fails
     */
    public static boolean requireAdmin(HttpSession session, jakarta.servlet.http.HttpServletResponse response)
            throws java.io.IOException {
        if (!isLoggedIn(session)) {
            response.sendRedirect(response.encodeRedirectURL("login"));
            return false;
        }

        if (!isAdmin(session)) {
            // User is logged in but not admin - show 403
            response.sendError(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN,
                    "Access denied. Admin privileges required.");
            return false;
        }

        return true;
    }

    /**
     * Check if user is admin without redirect
     * 
     * @param session HttpSession
     * @return true if logged in and admin
     */
    public static boolean isAdminLoggedIn(HttpSession session) {
        return isLoggedIn(session) && isAdmin(session);
    }

    /**
     * Memperbarui ID Sesi untuk mencegah Session Fixation Attack.
     * Dipanggil setiap kali user berhasil login.
     * * @param request The HTTP request
     */
    public static void refreshSession(HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            // Salin data lama, lalu buat sesi baru dengan ID baru
            User user = (User) oldSession.getAttribute(USER_KEY);
            oldSession.invalidate();
            
            HttpSession newSession = request.getSession(true);
            if (user != null) {
                login(newSession, user);
            }
        }
    }

    /**
     * Mendapatkan alamat IP user untuk keperluan audit/log.
     * * @param request The HTTP request
     * @return String alamat IP
     */
    public static String getUserIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
