package com.paulquiz.servlet;

import com.paulquiz.dao.ModuleDAO;
import com.paulquiz.dao.ContentDAO;
import com.paulquiz.model.Module;
import com.paulquiz.model.Content;
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
 * Module Servlet - handles module listing and detail views
 */
@WebServlet(name = "ModuleServlet", urlPatterns = { "/modules", "/modules/*" })
public class ModuleServlet extends HttpServlet {

    private ModuleDAO moduleDAO;
    private ContentDAO contentDAO;

    @Override
    public void init() {
        this.moduleDAO = new ModuleDAO();
        this.contentDAO = new ContentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // List all modules
                showModuleList(request, response);
            } else {
                // Show specific module detail
                showModuleDetail(request, response, pathInfo);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error in ModuleServlet", e);
        }
    }

    /**
     * Show list of all modules
     */
    private void showModuleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        // Get all modules
        List<Module> modules = moduleDAO.findAll();

        // Set attributes
        request.setAttribute("modules", modules);

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/views/modules/list.jsp")
                .forward(request, response);
    }

    /**
     * Show module detail with contents
     */
    private void showModuleDetail(HttpServletRequest request, HttpServletResponse response, String pathInfo)
            throws ServletException, IOException, SQLException {

        // Extract module ID from path
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Module not found");
            return;
        }

        try {
            Long moduleId = Long.parseLong(pathParts[1]);

            // Get module
            Module module = moduleDAO.findById(moduleId);
            if (module == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Module not found");
                return;
            }

            // Get contents for this module
            List<Content> contents = contentDAO.findByModule(moduleId);
            System.out.println("[DEBUG] ModuleServlet: Fetching contents for Module ID: " + moduleId);
            System.out.println(
                    "[DEBUG] ModuleServlet: Found " + (contents != null ? contents.size() : "null") + " items.");

            // Check if user is logged in for progress tracking
            HttpSession session = request.getSession(false);
            boolean isLoggedIn = SessionUtil.isLoggedIn(session);

            // Set attributes
            request.setAttribute("module", module);
            request.setAttribute("contents", contents);
            request.setAttribute("isLoggedIn", isLoggedIn);

            // Forward to JSP
            request.getRequestDispatcher("/WEB-INF/views/modules/detail.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid module ID");
        }
    }
}
