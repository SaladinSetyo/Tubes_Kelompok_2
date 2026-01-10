package com.paulquiz.servlet.admin;

import com.paulquiz.dao.ModuleDAO;
import com.paulquiz.model.Module;
import com.paulquiz.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ModuleManagementServlet", urlPatterns = { "/admin/modules", "/admin/modules/create",
        "/admin/modules/edit", "/admin/modules/delete" })
public class ModuleManagementServlet extends HttpServlet {

    private ModuleDAO moduleDAO;

    @Override
    public void init() {
        moduleDAO = new ModuleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Security Check: Ensure user is Admin
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getServletPath();

        try {
            switch (action) {
                case "/admin/modules/create":
                    showNewForm(request, response);
                    break;
                case "/admin/modules/edit":
                    showEditForm(request, response);
                    break;
                case "/admin/modules/delete":
                    deleteModule(request, response);
                    break;
                default:
                    listModules(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Security Check
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getServletPath();

        try {
            if ("/admin/modules/create".equals(action)) {
                insertModule(request, response);
            } else if ("/admin/modules/edit".equals(action)) {
                updateModule(request, response);
            } else {
                listModules(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listModules(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Module> listModules = moduleDAO.findAll();
        request.setAttribute("listModules", listModules);
        request.getRequestDispatcher("/WEB-INF/views/admin/module_list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/module_form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Module existingModule = moduleDAO.findById(id);
        request.setAttribute("module", existingModule);
        request.getRequestDispatcher("/WEB-INF/views/admin/module_form.jsp").forward(request, response);
    }

    private void insertModule(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String level = request.getParameter("level");
        int durationMinutes = Integer.parseInt(request.getParameter("durationMinutes"));
        int points = Integer.parseInt(request.getParameter("points"));
        String icon = request.getParameter("icon");
        int orderIndex = Integer.parseInt(request.getParameter("orderIndex"));

        Module newModule = new Module();
        newModule.setTitle(title);
        newModule.setDescription(description);
        newModule.setLevel(level);
        newModule.setDurationMinutes(durationMinutes);
        newModule.setPoints(points);
        newModule.setIcon(icon);
        newModule.setOrderIndex(orderIndex);

        moduleDAO.save(newModule);
        response.sendRedirect(request.getContextPath() + "/admin/modules");
    }

    private void updateModule(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String level = request.getParameter("level");
        int durationMinutes = Integer.parseInt(request.getParameter("durationMinutes"));
        int points = Integer.parseInt(request.getParameter("points"));
        String icon = request.getParameter("icon");
        int orderIndex = Integer.parseInt(request.getParameter("orderIndex"));

        Module module = new Module();
        module.setId(id);
        module.setTitle(title);
        module.setDescription(description);
        module.setLevel(level);
        module.setDurationMinutes(durationMinutes);
        module.setPoints(points);
        module.setIcon(icon);
        module.setOrderIndex(orderIndex);

        moduleDAO.update(module);
        response.sendRedirect(request.getContextPath() + "/admin/modules");
    }

    private void deleteModule(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        moduleDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/modules");
    }
}
