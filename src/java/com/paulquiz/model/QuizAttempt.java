package com.paulquiz.model;

import java.sql.Timestamp;

/**
 * QuizAttempt entity class representing quiz_attempts table
 */
public class QuizAttempt {
    private Long id;
    private Long userId;
    private Long quizId;
    private Integer score;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // For joined queries
    private String userName;
    private String quizTitle;

    // Constructors
    public QuizAttempt() {
    }

    public QuizAttempt(Long userId, Long quizId, Integer score) {
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    // Helper method
    public boolean isPerfectScore() {
        return score != null && score == 100;
    }

    @Override
    public String toString() {
        return "QuizAttempt{" +
                "id=" + id +
                ", userId=" + userId +
                ", quizId=" + quizId +
                ", score=" + score +
                '}';
    }
}

