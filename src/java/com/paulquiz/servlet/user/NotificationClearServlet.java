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

@WebServlet(name = "NotificationClearServlet", urlPatterns = { "/notifications/clear-read" })
public class NotificationClearServlet extends HttpServlet {

    private NotificationDAO notificationDAO;

    @Override
    public void init() throws ServletException {
        notificationDAO = new NotificationDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        if (user != null) {
            try {
                // Change behavior to Mark All As Read instead of Delete
                notificationDAO.markAllAsRead(user.getId());
            } catch (SQLException e) {
                e.printStackTrace();
                // Optionally set an error message in session
            }
        }

        // Redirect back to the previous page
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
}
