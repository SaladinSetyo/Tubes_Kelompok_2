package com.paulquiz.servlet.admin;

import com.paulquiz.dao.ModuleDAO;
import com.paulquiz.dao.QuizDAO;
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

@WebServlet(name = "QuizManagementServlet", urlPatterns = { "/admin/quizzes", "/admin/quizzes/create",
        "/admin/quizzes/edit", "/admin/quizzes/delete" })
public class QuizManagementServlet extends HttpServlet {

    private QuizDAO quizDAO;
    private ModuleDAO moduleDAO;

    @Override
    public void init() {
        quizDAO = new QuizDAO();
        moduleDAO = new ModuleDAO();
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
                case "/admin/quizzes/create":
                    showNewForm(request, response);
                    break;
                case "/admin/quizzes/edit":
                    showEditForm(request, response);
                    break;
                case "/admin/quizzes/delete":
                    deleteQuiz(request, response);
                    break;
                default:
                    listQuizzes(request, response);
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
            if ("/admin/quizzes/create".equals(action)) {
                insertQuiz(request, response);
            } else if ("/admin/quizzes/edit".equals(action)) {
                updateQuiz(request, response);
            } else {
                listQuizzes(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listQuizzes(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Quiz> listQuizzes = quizDAO.findAll();
        request.setAttribute("listQuizzes", listQuizzes);
        request.getRequestDispatcher("/WEB-INF/views/admin/quiz_list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Module> listModules = moduleDAO.findAll();
        request.setAttribute("listModules", listModules);
        request.getRequestDispatcher("/WEB-INF/views/admin/quiz_form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Quiz existingQuiz = quizDAO.findById(id);
        List<Module> listModules = moduleDAO.findAll();

        request.setAttribute("quiz", existingQuiz);
        request.setAttribute("listModules", listModules);
        request.getRequestDispatcher("/WEB-INF/views/admin/quiz_form.jsp").forward(request, response);
    }

    private void insertQuiz(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long moduleId = Long.parseLong(request.getParameter("moduleId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        Quiz newQuiz = new Quiz();
        newQuiz.setModuleId(moduleId);
        newQuiz.setTitle(title);
        newQuiz.setDescription(description);

        quizDAO.save(newQuiz);
        response.sendRedirect(request.getContextPath() + "/admin/quizzes");
    }

    private void updateQuiz(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        long moduleId = Long.parseLong(request.getParameter("moduleId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        Quiz quiz = new Quiz();
        quiz.setId(id);
        quiz.setModuleId(moduleId);
        quiz.setTitle(title);
        quiz.setDescription(description);

        quizDAO.update(quiz);
        response.sendRedirect(request.getContextPath() + "/admin/quizzes");
    }

    private void deleteQuiz(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        quizDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/quizzes");
    }
}
