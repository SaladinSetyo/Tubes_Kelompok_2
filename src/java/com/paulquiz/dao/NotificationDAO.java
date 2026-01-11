package com.paulquiz.dao;

import com.paulquiz.config.DbConnection;
import com.paulquiz.model.Notification;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotificationDAO {

    private Connection getConnection() throws SQLException {
        return DbConnection.getInstance().getConnection();
    }

    /**
     * Broadcast a message to ALL users
     * 
     * @param title   Title of the notification
     * @param message Body message
     * @param type    Notification type (info, warning, success)
     * @return Number of users reached
     */
    public int broadcast(String title, String message, String type) throws SQLException {
        // 1. Get all user IDs
        String getUserSql = "SELECT id FROM users";
        List<Long> userIds = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(getUserSql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                userIds.add(rs.getLong("id"));
            }
        }

        if (userIds.isEmpty())
            return 0;

        // 2. Batch insert notifications
        String sql = "INSERT INTO notifications (id, user_id, type, data, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            int count = 0;
            for (Long userId : userIds) {
                // Construct simple JSON data for portability
                String jsonData = String.format("{\"title\": \"%s\", \"message\": \"%s\"}",
                        title.replace("\"", "\\\""),
                        message.replace("\"", "\\\""));

                stmt.setString(1, UUID.randomUUID().toString());
                stmt.setLong(2, userId);
                stmt.setString(3, type);
                stmt.setString(4, jsonData);

                stmt.addBatch();
                count++;

                if (count % 100 == 0) {
                    stmt.executeBatch(); // Execute every 100 records
                }
            }
            stmt.executeBatch(); // Execute remaining
        }

        return userIds.size();
    }

    /**
     * Find notifications by user ID
     * 
     * @param userId User ID
     * @param limit  Maximum number of notifications
     * @return List of notifications
     * @throws SQLException if database error occurs
     */
    public List<Notification> findByUserId(Long userId, int limit) throws SQLException {
        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC LIMIT ?";
        List<Notification> notifications = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notifications.add(mapResultSetToNotification(rs));
                }
            }
        }
        return notifications;
    }

    /**
     * Mark notification as read
     * 
     * @param id Notification ID (UUID)
     * @return true if updated
     * @throws SQLException if database error occurs
     */
    public boolean markAsRead(String id) throws SQLException {
        String sql = "UPDATE notifications SET read_at = NOW() WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Count unread notifications for user
     * 
     * @param userId User ID
     * @return count of unread notifications
     * @throws SQLException if database error occurs
     */
    public int countUnread(Long userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM notifications WHERE user_id = ? AND read_at IS NULL";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    private Notification mapResultSetToNotification(ResultSet rs) throws SQLException {
        Notification notification = new Notification();
        notification.setId(rs.getString("id"));
        notification.setUserId(rs.getLong("user_id"));
        notification.setType(rs.getString("type"));
        notification.setData(rs.getString("data"));
        notification.setReadAt(rs.getTimestamp("read_at"));
        notification.setCreatedAt(rs.getTimestamp("created_at"));
        notification.setUpdatedAt(rs.getTimestamp("updated_at"));
        return notification;
    }

    /**
     * Delete all read notifications for a user
     * 
     * @param userId User ID
     * @return number of deleted notifications
     * @throws SQLException if database error occurs
     */
    public int deleteRead(Long userId) throws SQLException {
        String sql = "DELETE FROM notifications WHERE user_id = ? AND read_at IS NOT NULL";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, userId);
            return stmt.executeUpdate();
        }
    }

    /**
     * Mark ALL notifications as read for a user
     * 
     * @param userId User ID
     * @return number of notifications updated
     * @throws SQLException if database error occurs
     */
    public int markAllAsRead(Long userId) throws SQLException {
        String sql = "UPDATE notifications SET read_at = NOW() WHERE user_id = ? AND read_at IS NULL";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, userId);
            return stmt.executeUpdate();
        }
    }
}
