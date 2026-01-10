package com.paulquiz.dao;

import com.paulquiz.config.DbConnection;
import com.paulquiz.model.Answer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {

    private Connection getConnection() throws SQLException {
        return DbConnection.getInstance().getConnection();
    }

    public void save(Answer answer) throws SQLException {
        String sql = "INSERT INTO answers (question_id, answer_text, is_correct, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, answer.getQuestionId());
            stmt.setString(2, answer.getAnswerText());
            stmt.setBoolean(3, answer.getIsCorrect());
            stmt.executeUpdate();
        }
    }

    public void deleteByQuestionId(Long questionId) throws SQLException {
        String sql = "DELETE FROM answers WHERE question_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, questionId);
            stmt.executeUpdate();
        }
    }

    public List<Answer> findByQuestionId(Long questionId) throws SQLException {
        String sql = "SELECT * FROM answers WHERE question_id = ? ORDER BY id ASC";
        List<Answer> answers = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, questionId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Answer a = new Answer();
                    a.setId(rs.getLong("id"));
                    a.setQuestionId(rs.getLong("question_id"));
                    a.setAnswerText(rs.getString("answer_text"));
                    a.setIsCorrect(rs.getBoolean("is_correct"));
                    a.setCreatedAt(rs.getTimestamp("created_at"));
                    a.setUpdatedAt(rs.getTimestamp("updated_at"));
                    answers.add(a);
                }
            }
        }
        return answers;
    }
}
