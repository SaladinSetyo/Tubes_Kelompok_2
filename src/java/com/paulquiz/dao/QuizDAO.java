package com.paulquiz.dao;

import com.paulquiz.config.DbConnection;
import com.paulquiz.model.Quiz;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {

    private Connection getConnection() throws SQLException {
        return DbConnection.getInstance().getConnection();
    }

    public Quiz save(Quiz quiz) throws SQLException {
        String sql = "INSERT INTO quizzes (module_id, title, description) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, quiz.getModuleId());
            stmt.setString(2, quiz.getTitle());
            stmt.setString(3, quiz.getDescription());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        quiz.setId(rs.getLong(1));
                    }
                }
            }
            return quiz;
        }
    }

    public boolean update(Quiz quiz) throws SQLException {
        String sql = "UPDATE quizzes SET module_id = ?, title = ?, description = ? WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, quiz.getModuleId());
            stmt.setString(2, quiz.getTitle());
            stmt.setString(3, quiz.getDescription());
            stmt.setLong(4, quiz.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM quizzes WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public Quiz findById(Long id) throws SQLException {
        String sql = "SELECT q.*, m.title as module_name FROM quizzes q LEFT JOIN modules m ON q.module_id = m.id WHERE q.id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToQuiz(rs);
                }
            }
        }
        return null;
    }

    public List<Quiz> findAll() throws SQLException {
        String sql = "SELECT q.*, m.title as module_name FROM quizzes q LEFT JOIN modules m ON q.module_id = m.id ORDER BY q.module_id ASC, q.id ASC";
        List<Quiz> quizzes = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                quizzes.add(mapResultSetToQuiz(rs));
            }
        }
        return quizzes;
    }

    public List<Quiz> findByModuleId(Long moduleId) throws SQLException {
        String sql = "SELECT q.*, m.title as module_name FROM quizzes q LEFT JOIN modules m ON q.module_id = m.id WHERE q.module_id = ?";
        List<Quiz> quizzes = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, moduleId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    quizzes.add(mapResultSetToQuiz(rs));
                }
            }
        }
        return quizzes;
    }

    private Quiz mapResultSetToQuiz(ResultSet rs) throws SQLException {
        Quiz quiz = new Quiz();
        quiz.setId(rs.getLong("id"));
        quiz.setModuleId(rs.getLong("module_id"));
        quiz.setTitle(rs.getString("title"));
        quiz.setDescription(rs.getString("description"));
        quiz.setCreatedAt(rs.getTimestamp("created_at"));
        quiz.setUpdatedAt(rs.getTimestamp("updated_at"));
        if (hasColumn(rs, "module_name")) {
            quiz.setModuleName(rs.getString("module_name"));
        }
        return quiz;
    }

    private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnLabel(x))) {
                return true;
            }
        }
        return false;
    }
}
