package com.paulquiz.servlet.admin;

import com.paulquiz.dao.QuestionDAO;
import com.paulquiz.dao.QuizDAO;
import com.paulquiz.model.Question;
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

@WebServlet(name = "QuestionManagementServlet", urlPatterns = {
        "/admin/questions",
        "/admin/questions/create",
        "/admin/questions/edit",
        "/admin/questions/delete"
})
public class QuestionManagementServlet extends HttpServlet {

    private QuestionDAO questionDAO;
    private QuizDAO quizDAO;
    private com.paulquiz.dao.AnswerDAO answerDAO;

    @Override
    public void init() {
        questionDAO = new QuestionDAO();
        quizDAO = new QuizDAO();
        answerDAO = new com.paulquiz.dao.AnswerDAO();
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
                case "/admin/questions/create":
                    showNewForm(request, response);
                    break;
                case "/admin/questions/edit":
                    showEditForm(request, response);
                    break;
                case "/admin/questions/delete":
                    deleteQuestion(request, response);
                    break;
                default:
                    listQuestions(request, response);
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
            if ("/admin/questions/create".equals(action)) {
                insertQuestion(request, response);
            } else if ("/admin/questions/edit".equals(action)) {
                updateQuestion(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listQuestions(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String quizIdStr = request.getParameter("quizId");
        if (quizIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/admin/quizzes");
            return;
        }
        Long quizId = Long.parseLong(quizIdStr);
        Quiz quiz = quizDAO.findById(quizId);
        List<Question> listQuestions = questionDAO.findByQuizId(quizId);

        request.setAttribute("quiz", quiz);
        request.setAttribute("listQuestions", listQuestions);
        request.getRequestDispatcher("/WEB-INF/views/admin/question_list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String quizIdStr = request.getParameter("quizId");
        if (quizIdStr != null) {
            Long quizId = Long.parseLong(quizIdStr);
            Quiz quiz = quizDAO.findById(quizId);
            request.setAttribute("quiz", quiz);
        }
        request.getRequestDispatcher("/WEB-INF/views/admin/question_form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Question existingQuestion = questionDAO.findById(id);
        Quiz quiz = quizDAO.findById(existingQuestion.getQuizId());
        List<com.paulquiz.model.Answer> answers = answerDAO.findByQuestionId(id);

        request.setAttribute("question", existingQuestion);
        request.setAttribute("quiz", quiz);
        request.setAttribute("answers", answers);
        request.getRequestDispatcher("/WEB-INF/views/admin/question_form.jsp").forward(request, response);
    }

    private void insertQuestion(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long quizId = Long.parseLong(request.getParameter("quizId"));
        String questionText = request.getParameter("questionText");
        String description = request.getParameter("description");

        Question newQuestion = new Question();
        newQuestion.setQuizId(quizId);
        newQuestion.setQuestionText(questionText);
        newQuestion.setDescription(description);

        // Save Question and get ID
        newQuestion = questionDAO.save(newQuestion);

        // Save Answers
        saveAnswers(request, newQuestion.getId());

        response.sendRedirect(request.getContextPath() + "/admin/questions?quizId=" + quizId);
    }

    private void updateQuestion(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        long quizId = Long.parseLong(request.getParameter("quizId"));
        String questionText = request.getParameter("questionText");
        String description = request.getParameter("description");

        Question question = new Question();
        question.setId(id);
        question.setQuizId(quizId);
        question.setQuestionText(questionText);
        question.setDescription(description);

        questionDAO.update(question);

        // Delete old answers and re-insert new ones
        answerDAO.deleteByQuestionId(id);
        saveAnswers(request, id);

        response.sendRedirect(request.getContextPath() + "/admin/questions?quizId=" + quizId);
    }

    private void saveAnswers(HttpServletRequest request, Long questionId) throws SQLException {
        String correctAnswer = request.getParameter("correctAnswer"); // 'A', 'B', 'C', 'D'

        String[] options = { "A", "B", "C", "D" };

        for (String opt : options) {
            String text = request.getParameter("option" + opt);
            if (text != null && !text.trim().isEmpty()) {
                com.paulquiz.model.Answer ans = new com.paulquiz.model.Answer();
                ans.setQuestionId(questionId);
                ans.setAnswerText(text);
                ans.setIsCorrect(opt.equals(correctAnswer));
                answerDAO.save(ans);
            }
        }
    }

    private void deleteQuestion(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String quizIdStr = request.getParameter("quizId");

        // Find question to get quizId if not provided, for redirect
        if (quizIdStr == null) {
            Question q = questionDAO.findById(id);
            if (q != null)
                quizIdStr = String.valueOf(q.getQuizId());
        }

        questionDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/questions?quizId=" + quizIdStr);
    }
}
