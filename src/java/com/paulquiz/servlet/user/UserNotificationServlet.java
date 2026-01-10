package com.paulquiz.servlet.user;

import com.paulquiz.dao.NotificationDAO;
import com.paulquiz.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserNotificationServlet", urlPatterns = { "/notifications/mark-read",
        "/notifications/mark-all-read" })
public class UserNotificationServlet extends HttpServlet {

    private NotificationDAO notificationDAO;

    @Override
    public void init() {
        notificationDAO = new NotificationDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // If not logged in, just 401
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String action = request.getServletPath();

        try {
            if ("/notifications/mark-read".equals(action)) {
                String id = request.getParameter("id");
                if (id != null) {
                    notificationDAO.markAsRead(id);
                }
            } else if ("/notifications/mark-all-read".equals(action)) {
                notificationDAO.markAllAsRead(user.getId());
            }

            // Redirect back to where they came from
            String referer = request.getHeader("Referer");
            if (referer != null) {
                response.sendRedirect(referer);
            } else {
                response.sendRedirect(request.getContextPath() + "/dashboard");
            }

        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}
