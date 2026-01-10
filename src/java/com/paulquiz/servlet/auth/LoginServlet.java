package com.paulquiz.servlet.auth;

import com.paulquiz.dao.UserDAO;
import com.paulquiz.model.User;

import com.paulquiz.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Login servlet - handles user authentication
 */
@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // If already logged in, redirect to homepage
        if (SessionUtil.isLoggedIn(session)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        // Show login form
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate input
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            HttpSession session = request.getSession();
            SessionUtil.setErrorMessage(session, "Email dan password harus diisi");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Authenticate user
            User user = userDAO.authenticate(email.trim(), password);

            if (user != null) {
                // Login successful
                HttpSession session = request.getSession();
                SessionUtil.login(session, user);

                // Update last seen
                userDAO.updateLastSeen(user.getId());

                // Redirect to intended URL or homepage
                String intendedUrl = SessionUtil.getIntendedUrl(request);
                response.sendRedirect(intendedUrl);

            } else {
                // Login failed
                HttpSession session = request.getSession();
                SessionUtil.setErrorMessage(session, "Email atau password salah");
                response.sendRedirect(request.getContextPath() + "/login");
            }

        } catch (SQLException e) {
            throw new ServletException("Database error during login", e);
        }
    }
}
