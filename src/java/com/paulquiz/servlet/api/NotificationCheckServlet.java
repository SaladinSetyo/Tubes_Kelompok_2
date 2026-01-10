package com.paulquiz.servlet.api;

import com.paulquiz.dao.NotificationDAO;
import com.paulquiz.model.Notification;
import com.paulquiz.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "NotificationCheckServlet", urlPatterns = { "/api/notifications/check" })
public class NotificationCheckServlet extends HttpServlet {

    private NotificationDAO notificationDAO;

    @Override
    public void init() {
        notificationDAO = new NotificationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // JSON response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        if (user == null) {
            out.print("{\"unreadCount\": 0, \"notifications\": []}");
            out.flush();
            return;
        }

        try {
            int unreadCount = notificationDAO.countUnread(user.getId());
            List<Notification> latestNotifications = notificationDAO.findByUserId(user.getId(), 5);

            // Manual JSON construction to avoid dependencies like Gson/Jackson for now
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"unreadCount\": ").append(unreadCount).append(",");
            json.append("\"notifications\": [");

            for (int i = 0; i < latestNotifications.size(); i++) {
                Notification n = latestNotifications.get(i);
                json.append("{");
                json.append("\"id\": \"").append(n.getId()).append("\",");
                json.append("\"title\": \"").append(escapeJson(n.getTitle())).append("\",");
                // Escape message properly
                json.append("\"message\": \"").append(escapeJson(n.getMessage())).append("\",");
                json.append("\"type\": \"").append(n.getType()).append("\",");
                json.append("\"isUnread\": ").append(n.isUnread()).append(",");
                json.append("\"timeAgo\": \"").append(n.getTimeAgo()).append("\"");
                json.append("}");

                if (i < latestNotifications.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            json.append("}");

            out.print(json.toString());

        } catch (Exception e) {
            // Log error
            e.printStackTrace();
            out.print("{\"error\": \"Internal Server Error\"}");
        }

        out.flush();
    }

    private String escapeJson(String input) {
        if (input == null)
            return "";
        return input.replace("\"", "\\\"").replace("\n", " ").replace("\r", "");
    }
}
