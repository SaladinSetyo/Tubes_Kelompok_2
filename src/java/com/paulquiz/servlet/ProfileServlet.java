package com.paulquiz.servlet;

import com.paulquiz.model.User;
import com.paulquiz.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Profile Servlet - Displays user profile
 */
@WebServlet(name = "ProfileServlet", urlPatterns = { "/profile" })
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Ensure user is logged in
        HttpSession session = request.getSession(false);
        if (!SessionUtil.isLoggedIn(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // User object is already in session, but we could refresh it from DAO if needed
        // For now, just use session user
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
}
