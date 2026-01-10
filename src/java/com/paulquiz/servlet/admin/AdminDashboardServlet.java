package com.paulquiz.servlet.admin;

import com.paulquiz.config.DbConnection;
import com.paulquiz.dao.UserDAO;
import com.paulquiz.dao.ModuleDAO;
import com.paulquiz.dao.ContentDAO;
import com.paulquiz.util.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Admin Dashboard Servlet
 * Displays admin panel homepage with statistics and quick actions
 */
@WebServlet(name = "AdminDashboardServlet", urlPatterns = { "/admin", "/admin/dashboard" })
public class AdminDashboardServlet extends HttpServlet {

    private UserDAO userDAO;
    private ModuleDAO moduleDAO;
    private ContentDAO contentDAO;

    @Override
    public void init() {
        this.userDAO = new UserDAO();
        this.moduleDAO = new ModuleDAO();
        this.contentDAO = new ContentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Require admin access
        if (!SessionUtil.requireAdmin(session, response)) {
            return; // Already redirected or shown error
        }

        try {
            // Get statistics for dashboard
            int totalUsers = getTotalUsers();
            int totalModules = moduleDAO.findAll().size();
            int totalContents = contentDAO.findAll().size();
            int totalQuizzes = getTotalQuizzes();
            int totalQuizAttempts = getTotalQuizAttempts();
            int onlineUsers = getOnlineUsersCount();

            // Set attributes for JSP
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalModules", totalModules);
            request.setAttribute("totalContents", totalContents);
            request.setAttribute("totalQuizzes", totalQuizzes);
            request.setAttribute("totalQuizAttempts", totalQuizAttempts);
            request.setAttribute("onlineUsers", onlineUsers);

            // Get recent activity (last 10 users registered)
            request.setAttribute("recentUsers", userDAO.getRecentUsers(10));

            // Forward to admin dashboard JSP
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error in admin dashboard", e);
        }
    }

    /**
     * Get total number of users
     */
    private int getTotalUsers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection conn = DbConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    /**
     * Get total number of quizzes
     */
    private int getTotalQuizzes() throws SQLException {
        String sql = "SELECT COUNT(*) FROM quizzes";
        try (Connection conn = DbConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    /**
     * Get total number of quiz attempts
     */
    private int getTotalQuizAttempts() throws SQLException {
        String sql = "SELECT COUNT(*) FROM quiz_attempts";
        try (Connection conn = DbConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    /**
     * Get number of currently online users
     */
    private int getOnlineUsersCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE last_seen_at > DATE_SUB(NOW(), INTERVAL 5 MINUTE)";
        try (Connection conn = DbConnection.getInstance().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
