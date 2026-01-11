package com.paulquiz.servlet.user;

import com.paulquiz.dao.AnswerDAO;
import com.paulquiz.dao.QuestionDAO;
import com.paulquiz.dao.QuizDAO;
import com.paulquiz.model.Answer;
import com.paulquiz.model.Question;
import com.paulquiz.model.Quiz;
import com.paulquiz.model.User;
import com.paulquiz.model.QuizAttempt;
import com.paulquiz.dao.QuizAttemptDAO;
import com.paulquiz.dao.UserDAO;
import com.paulquiz.dao.ModuleDAO;
import com.paulquiz.model.Module;
import com.paulquiz.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "QuizAttemptServlet", urlPatterns = { "/quizzes/*" })
public class QuizAttemptServlet extends HttpServlet {

    private QuizDAO quizDAO;
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private QuizAttemptDAO quizAttemptDAO; // Added field
    private UserDAO userDAO;
    private ModuleDAO moduleDAO;

    @Override
    public void init() {
        quizDAO = new QuizDAO();
        questionDAO = new QuestionDAO();
        answerDAO = new AnswerDAO();
        quizAttemptDAO = new QuizAttemptDAO(); // Initialized field
        userDAO = new UserDAO();
        moduleDAO = new ModuleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (!SessionUtil.isLoggedIn(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz ID mismatch");
            return;
        }

        try {
            // Extract quiz ID (e.g., /1 -> 1)
            Long quizId = extractQuizId(pathInfo); // Refactored to use helper method
            if (quizId == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found");
                return;
            }

            Quiz quiz = quizDAO.findById(quizId);
            if (quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found in DB");
                return;
            }

            // Fetch questions
            List<Question> questions = questionDAO.findByQuizId(quizId);

            // Map questions to their answers
            Map<Long, List<Answer>> questionsAnswers = new HashMap<>();
            for (Question q : questions) {
                List<Answer> answers = answerDAO.findByQuestionId(q.getId());
                questionsAnswers.put(q.getId(), answers);
            }

            request.setAttribute("quiz", quiz);
            request.setAttribute("questions", questions);
            request.setAttribute("questionsAnswers", questionsAnswers);

            request.getRequestDispatcher("/WEB-INF/views/user/quiz_attempt.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Quiz ID");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (!SessionUtil.isLoggedIn(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");

        String pathInfo = request.getPathInfo();
        try {
            Long quizId = extractQuizId(pathInfo);
            if (quizId == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found");
                return;
            }

            Quiz quiz = quizDAO.findById(quizId);
            List<Question> questions = questionDAO.findByQuizId(quizId);

            int correctCount = 0;
            int totalQuestions = questions.size();

            for (Question q : questions) {
                String paramName = "question_" + q.getId();
                String submittedAnswerIdStr = request.getParameter(paramName);

                if (submittedAnswerIdStr != null) {
                    Long submittedAnswerId = Long.parseLong(submittedAnswerIdStr);
                    List<Answer> correctAnswers = answerDAO.findByQuestionId(q.getId());

                    // Check if submitted answer is correct
                    for (Answer ans : correctAnswers) {
                        if (ans.getId().equals(submittedAnswerId) && ans.getIsCorrect()) {
                            correctCount++;
                            break;
                        }
                    }
                }
            }

            int score = (totalQuestions > 0) ? (int) Math.round(((double) correctCount / totalQuestions) * 100) : 0;

            // Check if user previously passed this quiz
            Integer bestScore = quizAttemptDAO.getBestScore(user.getId(), quizId);
            boolean alreadyPassed = (bestScore != null && bestScore >= 70);

            System.out.println("[DEBUG] Quiz Submission: User=" + user.getId() + ", Quiz=" + quizId + ", Score=" + score
                    + ", PreviousBest=" + bestScore + ", AlreadyPassed=" + alreadyPassed);

            // Save Attempt
            QuizAttempt attempt = new QuizAttempt();
            attempt.setUserId(user.getId());
            attempt.setQuizId(quizId);
            attempt.setScore(score);
            quizAttemptDAO.create(attempt);

            // Award points if passed (score >= 70) and never passed before
            boolean pointsAwarded = false;
            int pointsEarned = 0;

            if (score >= 70 && !alreadyPassed) {
                Module module = moduleDAO.findById(quiz.getModuleId());
                System.out.println("[DEBUG] Module found: " + (module != null ? module.getId() : "null"));
                if (module != null) {
                    pointsEarned = module.getPoints();
                    System.out.println("[DEBUG] Awarding Points: " + pointsEarned);
                    userDAO.addPoints(user.getId(), pointsEarned);

                    // Update session
                    user.setPoints(user.getPoints() + pointsEarned);
                    session.setAttribute("user", user);
                    pointsAwarded = true;
                }
            } else {
                System.out.println("[DEBUG] No points awarded. Condition: Score>=70 (" + (score >= 70)
                        + ") && !AlreadyPassed (" + (!alreadyPassed) + ")");
            }

            request.setAttribute("quiz", quiz);
            request.setAttribute("score", score);
            request.setAttribute("correctCount", correctCount);
            request.setAttribute("totalQuestions", totalQuestions);
            request.setAttribute("pointsAwarded", pointsAwarded);
            request.setAttribute("pointsEarned", pointsEarned);
            request.setAttribute("alreadyPassed", alreadyPassed);

            request.getRequestDispatcher("/WEB-INF/views/user/quiz_result.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error during quiz submission", e);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input format");
        }
    }

}
