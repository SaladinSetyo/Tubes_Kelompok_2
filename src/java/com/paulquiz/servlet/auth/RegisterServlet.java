package com.paulquiz.servlet.auth;

import com.paulquiz.dao.UserDAO;
import com.paulquiz.model.User;
import com.paulquiz.util.PasswordUtil;
import com.paulquiz.util.SessionUtil;
import com.paulquiz.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Register servlet - handles user registration
 */
@WebServlet(name = "RegisterServlet", urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {

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

        // Show registration form
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("password_confirmation");

        HttpSession session = request.getSession();

        // Validate input
        if (!ValidationUtil.isNotEmpty(name)) {
            SessionUtil.setErrorMessage(session, "Nama harus diisi");
            response.sendRedirect(request.getContextPath() + "/register");
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            SessionUtil.setErrorMessage(session, "Format email tidak valid");
            response.sendRedirect(request.getContextPath() + "/register");
            return;
        }

        if (!PasswordUtil.isPasswordStrong(password)) {
            SessionUtil.setErrorMessage(session, PasswordUtil.getPasswordStrengthMessage(password));
            response.sendRedirect(request.getContextPath() + "/register");
            return;
        }

        if (!password.equals(passwordConfirmation)) {
            SessionUtil.setErrorMessage(session, "Password dan konfirmasi password tidak sama");
            response.sendRedirect(request.getContextPath() + "/register");
            return;
        }

        try {
            // Check if email already exists
            if (userDAO.emailExists(email.trim())) {
                SessionUtil.setErrorMessage(session, "Email sudah terdaftar");
                response.sendRedirect(request.getContextPath() + "/register");
                return;
            }

            // Create new user
            User user = new User();
            user.setName(name.trim());
            user.setEmail(email.trim());
            user.setPassword(PasswordUtil.hashPassword(password)); // Hash password
            user.setRole("user");
            user.setPoints(0);

            userDAO.create(user);

            // Show success message and redirect to login
            SessionUtil.setSuccessMessage(session, "Registrasi berhasil! Silakan login.");
            response.sendRedirect(request.getContextPath() + "/login");

        } catch (SQLException e) {
            throw new ServletException("Database error during registration", e);
        }
    }
}

