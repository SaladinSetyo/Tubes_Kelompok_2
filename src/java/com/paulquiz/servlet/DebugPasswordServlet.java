package com.paulquiz.servlet;

import com.paulquiz.dao.UserDAO;
import com.paulquiz.model.User;
import com.paulquiz.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Debug servlet for testing password hashing and authentication
 */
@WebServlet(name = "DebugPasswordServlet", urlPatterns = { "/debug/password" })
public class DebugPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Password Debug</title>");
        out.println("<style>body{font-family:monospace;padding:20px;background:#1a1a1a;color:#0f0;}</style>");
        out.println("</head><body>");
        out.println("<h1>🔐 Password Debugging Tool</h1>");

        // Test 1: Generate hash for 'admin123'
        out.println("<h2>Test 1: Generate BCrypt Hash</h2>");
        String plainPassword = "admin123";
        String hash = PasswordUtil.hashPassword(plainPassword);
        out.println("<p><strong>Plain Password:</strong> " + plainPassword + "</p>");
        out.println("<p><strong>BCrypt Hash:</strong> " + hash + "</p>");
        out.println("<p style='color:yellow;'><em>Copy this hash and update database!</em></p>");

        // Test 2: Verify against existing hash
        out.println("<h2>Test 2: Verify Password Against Hashes</h2>");

        String[] testHashes = {
                "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi",
                "$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5UDQPWBNOxu8.",
                hash
        };

        for (int i = 0; i < testHashes.length; i++) {
            boolean matches = PasswordUtil.verifyPassword(plainPassword, testHashes[i]);
            out.println("<p>Hash " + (i + 1) + ": " + (matches ? "✅ MATCH" : "❌ NO MATCH") + "</p>");
            out.println("<p style='font-size:10px;'>" + testHashes[i] + "</p>");
        }

        // Test 3: Try to authenticate admin user
        out.println("<h2>Test 3: Database Authentication</h2>");
        UserDAO userDAO = new UserDAO();

        try {
            // Find user by email
            User user = userDAO.findByEmail("admin@paulquiz.com");

            if (user != null) {
                out.println("<p><strong>✅ User found in database</strong></p>");
                out.println("<p>ID: " + user.getId() + "</p>");
                out.println("<p>Name: " + user.getName() + "</p>");
                out.println("<p>Email: " + user.getEmail() + "</p>");
                out.println("<p>Role: " + user.getRole() + "</p>");
                out.println("<p>Stored Password Hash: " + user.getPassword() + "</p>");

                // Try to authenticate
                out.println("<h3>Authentication Test:</h3>");
                User authUser = userDAO.authenticate("admin@paulquiz.com", plainPassword);

                if (authUser != null) {
                    out.println("<p style='color:lime;font-size:20px;'>✅ AUTHENTICATION SUCCESS!</p>");
                } else {
                    out.println("<p style='color:red;font-size:20px;'>❌ AUTHENTICATION FAILED</p>");

                    // Try manual verification
                    boolean manualCheck = PasswordUtil.verifyPassword(plainPassword, user.getPassword());
                    out.println("<p>Manual BCrypt check: " + (manualCheck ? "✅ PASS" : "❌ FAIL") + "</p>");

                    // Check if password is plain text
                    boolean plainTextMatch = plainPassword.equals(user.getPassword());
                    out.println("<p>Plain text check: "
                            + (plainTextMatch ? "✅ PASS (PASSWORD IS PLAIN TEXT!)" : "❌ FAIL") + "</p>");
                }

            } else {
                out.println("<p style='color:red;'><strong>❌ User NOT found in database</strong></p>");
                out.println("<p>Make sure admin@paulquiz.com exists in users table</p>");
            }

        } catch (SQLException e) {
            out.println("<p style='color:red;'><strong>❌ Database Error:</strong></p>");
            out.println("<pre>" + e.getMessage() + "</pre>");
            e.printStackTrace(out);
        }

        // SQL Update command
        out.println("<h2>📋 SQL Update Command</h2>");
        out.println("<p>Run this in phpMyAdmin to update admin password:</p>");
        out.println("<pre style='background:#333;padding:10px;border-radius:5px;'>");
        out.println("UPDATE users SET password = '" + hash + "' WHERE email = 'admin@paulquiz.com';");
        out.println("</pre>");

        out.println("</body></html>");
    }
}
