package com.paulquiz.model;

import java.sql.Timestamp;

/**
 * UserProgress entity class representing user_progress table
 */
public class UserProgress {
    private Long id;
    private Long userId;
    private Long contentId;
    private Integer score;
    private Timestamp completedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // For joined queries
    private String contentTitle;
    private String userName;

    // Constructors
    public UserProgress() {
    }

    public UserProgress(Long userId, Long contentId) {
        this.userId = userId;
        this.contentId = contentId;
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

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
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

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserProgress{" +
                "id=" + id +
                ", userId=" + userId +
                ", contentId=" + contentId +
                ", score=" + score +
                '}';
    }
}

