package com.paulquiz.model;

import java.sql.Timestamp;

/**
 * Notification entity class representing notifications table
 */
public class Notification {
    private String id; // UUID
    private Long userId;
    private String type;
    private String data; // JSON string
    private Timestamp readAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // For joined queries
    private String userName;

    // Constructors
    public Notification() {
    }

    public Notification(String id, Long userId, String type, String data) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.data = data;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Timestamp getReadAt() {
        return readAt;
    }

    public void setReadAt(Timestamp readAt) {
        this.readAt = readAt;
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

    // Helper methods
    public boolean isRead() {
        return readAt != null;
    }

    public boolean isUnread() {
        return readAt == null;
    }

    // Helper methods for JSON data
    public String getTitle() {
        if (data == null || !data.contains("\"title\":"))
            return "Notification";
        try {
            int start = data.indexOf("\"title\":") + 9;
            int end = data.indexOf("\"", start + 1); // finding end quote
            // Handle escaped quotes if simple, but for now assume simple
            return data.substring(start + 1, end);
        } catch (Exception e) {
            return "Notification";
        }
    }

    public String getMessage() {
        if (data == null || !data.contains("\"message\":"))
            return "";
        try {
            int start = data.indexOf("\"message\":") + 11;
            int end = data.lastIndexOf("\""); // Simple assumption for last quote
            // better matching: find end quote that matches the start quote's closure
            // But since we built it with format "message": "...", let's just find the next
            // quote
            end = data.indexOf("\"", start + 1);
            return data.substring(start + 1, end);
        } catch (Exception e) {
            return "";
        }
    }

    public String getTimeAgo() {
        if (createdAt == null)
            return "";
        long diff = System.currentTimeMillis() - createdAt.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (seconds < 60)
            return "Baru saja";
        if (minutes < 60)
            return minutes + " menit yll";
        if (hours < 24)
            return hours + " jam yll";
        return days + " hari yll";
    }
}
