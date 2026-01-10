package com.paulquiz.dao;

import com.paulquiz.config.DbConnection;
import com.paulquiz.model.QuizAttempt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for QuizAttempt operations
 */
public class QuizAttemptDAO {

    private Connection getConnection() throws SQLException {
        return DbConnection.getInstance().getConnection();
    }

    /**
     * Create a new quiz attempt
     * 
     * @param attempt QuizAttempt object to create
     * @return Created attempt with ID set
     * @throws SQLException if database error occurs
     */
    public QuizAttempt create(QuizAttempt attempt) throws SQLException {
        String sql = "INSERT INTO quiz_attempts (user_id, quiz_id, score) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, attempt.getUserId());
            stmt.setLong(2, attempt.getQuizId());
            stmt.setInt(3, attempt.getScore());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        attempt.setId(rs.getLong(1));
                    }
                }
            }

            return attempt;
        }
    }

    /**
     * Find quiz attempts by user and quiz
     * 
     * @param userId User ID
     * @param quizId Quiz ID
     * @return List of attempts
     * @throws SQLException if database error occurs
     */
    public List<QuizAttempt> findByUserAndQuiz(Long userId, Long quizId) throws SQLException {
        String sql = "SELECT qa.*, u.name as user_name, q.title as quiz_title " +
                "FROM quiz_attempts qa " +
                "LEFT JOIN users u ON qa.user_id = u.id " +
                "LEFT JOIN quizzes q ON qa.quiz_id = q.id " +
                "WHERE qa.user_id = ? AND qa.quiz_id = ? " +
                "ORDER BY qa.created_at DESC";
        List<QuizAttempt> attempts = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, quizId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    attempts.add(mapResultSetToQuizAttempt(rs));
                }
            }
        }

        return attempts;
    }

    /**
     * Find quiz attempts by user
     * 
     * @param userId User ID
     * @return List of attempts
     * @throws SQLException if database error occurs
     */
    public List<QuizAttempt> findByUser(Long userId) throws SQLException {
        String sql = "SELECT qa.*, u.name as user_name, q.title as quiz_title " +
                "FROM quiz_attempts qa " +
                "LEFT JOIN users u ON qa.user_id = u.id " +
                "LEFT JOIN quizzes q ON qa.quiz_id = q.id " +
                "WHERE qa.user_id = ? " +
                "ORDER BY qa.created_at DESC";
        List<QuizAttempt> attempts = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    attempts.add(mapResultSetToQuizAttempt(rs));
                }
            }
        }

        return attempts;
    }

    /**
     * Check if user has perfect score (100) for a quiz
     * 
     * @param userId User ID
     * @param quizId Quiz ID
     * @return true if user has perfect score, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean hasPerfectScore(Long userId, Long quizId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM quiz_attempts WHERE user_id = ? AND quiz_id = ? AND score = 100";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, quizId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    /**
     * Get best score for a quiz by user
     * 
     * @param userId User ID
     * @param quizId Quiz ID
     * @return Best score or null if no attempt found
     * @throws SQLException if database error occurs
     */
    public Integer getBestScore(Long userId, Long quizId) throws SQLException {
        String sql = "SELECT MAX(score) as best_score FROM quiz_attempts WHERE user_id = ? AND quiz_id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, quizId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int score = rs.getInt("best_score");
                    return rs.wasNull() ? null : score;
                }
            }
        }

        return null;
    }

    /**
     * Map ResultSet to QuizAttempt object
     * 
     * @param rs ResultSet
     * @return QuizAttempt object
     * @throws SQLException if database error occurs
     */
    private QuizAttempt mapResultSetToQuizAttempt(ResultSet rs) throws SQLException {
        QuizAttempt attempt = new QuizAttempt();
        attempt.setId(rs.getLong("id"));
        attempt.setUserId(rs.getLong("user_id"));
        attempt.setQuizId(rs.getLong("quiz_id"));
        attempt.setScore(rs.getInt("score"));
        attempt.setCreatedAt(rs.getTimestamp("created_at"));
        attempt.setUpdatedAt(rs.getTimestamp("updated_at"));

        // Joined data
        attempt.setUserName(rs.getString("user_name"));
        attempt.setQuizTitle(rs.getString("quiz_title"));

        return attempt;
    }
}

