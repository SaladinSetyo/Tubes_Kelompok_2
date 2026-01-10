package com.paulquiz.servlet;

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
import java.util.List;

/**
 * Leaderboard Servlet - Displays top users
 */
@WebServlet(name = "LeaderboardServlet", urlPatterns = { "/leaderboard" })
public class LeaderboardServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Ensure user is logged in
        HttpSession session = request.getSession(false);
        if (!SessionUtil.isLoggedIn(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Get top 10 users by points
            List<User> topUsers = userDAO.getLeaderboard(10);
            request.setAttribute("topUsers", topUsers);

            // Forward to JSP
            request.getRequestDispatcher("/WEB-INF/views/leaderboard.jsp").forward(request, response);

        } catch (SQLException e) {
            System.err.println("Error calculating leaderboard: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}
