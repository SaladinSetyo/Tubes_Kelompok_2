package com.paulquiz.servlet.admin;

import com.paulquiz.dao.ContentDAO;
import com.paulquiz.dao.ModuleDAO;
import com.paulquiz.dao.QuizDAO;
import com.paulquiz.model.Content;
import com.paulquiz.model.Module;
import com.paulquiz.model.Quiz;
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

@WebServlet(name = "ContentManagementServlet", urlPatterns = { "/admin/contents", "/admin/contents/create",
        "/admin/contents/edit", "/admin/contents/delete" })
public class ContentManagementServlet extends HttpServlet {

    private ContentDAO contentDAO;
    private ModuleDAO moduleDAO;
    private QuizDAO quizDAO;

    @Override
    public void init() {
        contentDAO = new ContentDAO();
        moduleDAO = new ModuleDAO();
        quizDAO = new QuizDAO(); // To link content to quiz if needed
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

        String action = request.getServletPath();

        try {
            switch (action) {
                case "/admin/contents/create":
                    showNewForm(request, response);
                    break;
                case "/admin/contents/edit":
                    showEditForm(request, response);
                    break;
                case "/admin/contents/delete":
                    deleteContent(request, response);
                    break;
                default:
                    listContents(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
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

        String action = request.getServletPath();

        try {
            if ("/admin/contents/create".equals(action)) {
                insertContent(request, response);
            } else if ("/admin/contents/edit".equals(action)) {
                updateContent(request, response);
            } else {
                listContents(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listContents(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String moduleIdStr = request.getParameter("moduleId");
        List<Content> listContents;

        if (moduleIdStr != null && !moduleIdStr.isEmpty()) {
            long moduleId = Long.parseLong(moduleIdStr);
            listContents = contentDAO.findByModule(moduleId);
            request.setAttribute("selectedModuleId", moduleId);
        } else {
            listContents = contentDAO.findAll();
        }

        request.setAttribute("listContents", listContents);
        request.getRequestDispatcher("/WEB-INF/views/admin/content_list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Module> listModules = moduleDAO.findAll();
        List<Quiz> listQuizzes = quizDAO.findAll(); // If content type is quiz

        String moduleIdStr = request.getParameter("moduleId");
        if (moduleIdStr != null && !moduleIdStr.isEmpty()) {
            request.setAttribute("selectedModuleId", Long.parseLong(moduleIdStr));
        }

        request.setAttribute("listModules", listModules);
        request.setAttribute("listQuizzes", listQuizzes);
        request.getRequestDispatcher("/WEB-INF/views/admin/content_form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Content existingContent = contentDAO.findById(id);
        List<Module> listModules = moduleDAO.findAll();
        List<Quiz> listQuizzes = quizDAO.findAll();

        request.setAttribute("content", existingContent);
        request.setAttribute("listModules", listModules);
        request.setAttribute("listQuizzes", listQuizzes);
        request.getRequestDispatcher("/WEB-INF/views/admin/content_form.jsp").forward(request, response);
    }

    private void insertContent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long moduleId = Long.parseLong(request.getParameter("moduleId"));
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String body = request.getParameter("body");
        String mediaUrl = request.getParameter("mediaUrl");
        int order = Integer.parseInt(request.getParameter("order"));
        String description = request.getParameter("description");

        String quizIdStr = request.getParameter("quizId");
        Long quizId = (quizIdStr != null && !quizIdStr.isEmpty()) ? Long.parseLong(quizIdStr) : null;

        boolean isFeatured = request.getParameter("isFeatured") != null;

        Content newContent = new Content();
        newContent.setModuleId(moduleId);
        newContent.setTitle(title);
        newContent.setType(type);
        newContent.setBody(body);
        newContent.setMediaUrl(mediaUrl);
        newContent.setOrder(order);
        newContent.setQuizId(quizId);
        newContent.setDescription(description);
        newContent.setIsFeatured(isFeatured);

        contentDAO.create(newContent);
        response.sendRedirect(request.getContextPath() + "/admin/contents");
    }

    private void updateContent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        long moduleId = Long.parseLong(request.getParameter("moduleId"));
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String body = request.getParameter("body");
        String mediaUrl = request.getParameter("mediaUrl");
        int order = Integer.parseInt(request.getParameter("order"));
        String description = request.getParameter("description");

        String quizIdStr = request.getParameter("quizId");
        Long quizId = (quizIdStr != null && !quizIdStr.isEmpty()) ? Long.parseLong(quizIdStr) : null;

        boolean isFeatured = request.getParameter("isFeatured") != null;

        Content content = new Content();
        content.setId(id);
        content.setModuleId(moduleId);
        content.setTitle(title);
        content.setType(type);
        content.setBody(body);
        content.setMediaUrl(mediaUrl);
        content.setOrder(order);
        content.setQuizId(quizId);
        content.setDescription(description);
        content.setIsFeatured(isFeatured);

        contentDAO.update(content);
        response.sendRedirect(request.getContextPath() + "/admin/contents");
    }

    private void deleteContent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        contentDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/contents");
    }
}
