package com.paulquiz.servlet.admin;

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

@WebServlet(name = "BroadcastServlet", urlPatterns = { "/admin/notifications" })
public class BroadcastServlet extends HttpServlet {

    private NotificationDAO notificationDAO;

    @Override
    public void init() {
        notificationDAO = new NotificationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/broadcast_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        String message = request.getParameter("message");
        String type = request.getParameter("type"); // info, success, warning, error

        try {
            int count = notificationDAO.broadcast(title, message, type);
            request.setAttribute("successMessage", "Berhasil mengirim notifikasi ke " + count + " pengguna.");
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/broadcast_form.jsp").forward(request, response);
    }
}
