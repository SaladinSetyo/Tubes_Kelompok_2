package com.paulquiz.servlet;

import com.paulquiz.dao.ContentDAO;
import com.paulquiz.dao.ModuleDAO;
import com.paulquiz.dao.QuizAttemptDAO;
import com.paulquiz.dao.UserDAO;
import com.paulquiz.model.Content;
import com.paulquiz.model.Module;
import com.paulquiz.model.User;
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
 * Homepage servlet - displays main landing page
 */
@WebServlet(name = "HomepageServlet", urlPatterns = { "/index", "/home" })
public class HomepageServlet extends HttpServlet {

    private ContentDAO contentDAO;
    private ModuleDAO moduleDAO;
    private UserDAO userDAO;
    private QuizAttemptDAO quizAttemptDAO;

    @Override
    public void init() {
        contentDAO = new ContentDAO();
        moduleDAO = new ModuleDAO();
        userDAO = new UserDAO();
        quizAttemptDAO = new QuizAttemptDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get infographics
            List<Content> infographics = contentDAO.findByType("infographic", 3);
            request.setAttribute("infographics", infographics);

            // Get videos
            List<Content> videos = contentDAO.findByType("video", 3);
            request.setAttribute("videos", videos);

            // Get first module for "Mulai Belajar" button
            Module firstModule = moduleDAO.getFirstModule();
            request.setAttribute("firstModule", firstModule);

            // Get featured quiz
            Content featuredQuiz = contentDAO.findFeaturedQuiz();
            request.setAttribute("featuredQuiz", featuredQuiz);

            // Check if user has solved featured quiz (if logged in)
            HttpSession session = request.getSession(false);
            boolean isFeaturedQuizSolved = false;

            if (SessionUtil.isLoggedIn(session) && featuredQuiz != null && featuredQuiz.getQuizId() != null) {
                Long userId = SessionUtil.getCurrentUserId(session);
                isFeaturedQuizSolved = quizAttemptDAO.hasPerfectScore(userId, featuredQuiz.getQuizId());
            }

            request.setAttribute("isFeaturedQuizSolved", isFeaturedQuizSolved);

            // Get online users (if logged in)
            List<User> onlineUsers = null;
            if (SessionUtil.isLoggedIn(session)) {
                onlineUsers = userDAO.getOnlineUsers(10);
            }
            request.setAttribute("onlineUsers", onlineUsers);

        } catch (SQLException e) {
            // Database error - log it but continue with empty data
            System.err.println("Database error in HomepageServlet: " + e.getMessage());
            e.printStackTrace();

            // Set empty attributes so JSP doesn't break
            request.setAttribute("infographics", new java.util.ArrayList<>());
            request.setAttribute("videos", new java.util.ArrayList<>());
            request.setAttribute("firstModule", null);
            request.setAttribute("featuredQuiz", null);
            request.setAttribute("isFeaturedQuizSolved", false);
            request.setAttribute("onlineUsers", null);
        }

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
}

