package com.paulquiz.dao;

import com.paulquiz.config.DbConnection;
import com.paulquiz.model.User;
import com.paulquiz.util.PasswordUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for User operations
 */
public class UserDAO {

    private Connection getConnection() throws SQLException {
        return DbConnection.getInstance().getConnection();
    }

    /**
     * Create a new user
     * 
     * @param user User object to create
     * @return Created user with ID set
     * @throws SQLException if database error occurs
     */
    public User create(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, role, points) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword()); // Should already be hashed
            stmt.setString(4, user.getRole());
            stmt.setInt(5, user.getPoints());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getLong(1));
                    }
                }
            }

            return user;
        }
    }

    /**
     * Find user by ID
     * 
     * @param id User ID
     * @return User object or null if not found
     * @throws SQLException if database error occurs
     */
    public User findById(Long id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }

        return null;
    }

    /**
     * Find user by email
     * 
     * @param email User email
     * @return User object or null if not found
     * @throws SQLException if database error occurs
     */
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }

        return null;
    }

    /**
     * Update user's last seen timestamp
     * 
     * @param userId User ID
     * @return true if updated
     * @throws SQLException
     */
    public boolean updateLastSeen(Long userId) throws SQLException {
        String sql = "UPDATE users SET last_seen_at = NOW() WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Count users active in the last N minutes
     * 
     * @param minutesThreshold Minutes to look back
     * @return Count of active users
     * @throws SQLException
     */
    public int countOnlineUsers(int minutesThreshold) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE last_seen_at >= NOW() - INTERVAL ? MINUTE";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, minutesThreshold);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    /**
     * Authenticate user
     * 
     * @param email    User email
     * @param password Plain text password
     * @return User object if authentication successful, null otherwise
     * @throws SQLException if database error occurs
     */

    public User authenticate(String email, String password) throws SQLException {
        User user = findByEmail(email);

        if (user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    /**
     * Update user
     * 
     * @param user User object with updated data
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ?, points = ?, role = ?, last_seen_at = ? WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getPoints());
            stmt.setString(4, user.getRole());
            stmt.setTimestamp(5, user.getLastSeenAt());
            stmt.setLong(6, user.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Update user password
     * 
     * @param userId          User ID
     * @param newPasswordHash New hashed password
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean updatePassword(Long userId, String newPasswordHash) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, newPasswordHash);
            stmt.setLong(2, userId);

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Add points to user
     * 
     * @param userId User ID
     * @param points Points to add
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean addPoints(Long userId, int points) throws SQLException {
        String sql = "UPDATE users SET points = COALESCE(points, 0) + ? WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, points);
            stmt.setLong(2, userId);

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Get leaderboard (users sorted by points)
     * 
     * @param limit Maximum number of users to return
     * @return List of users ordered by points
     * @throws SQLException if database error occurs
     */
    public List<User> getLeaderboard(int limit) throws SQLException {
        String sql = "SELECT * FROM users ORDER BY points DESC, name ASC LIMIT ?";
        List<User> users = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        }

        return users;
    }

    /**
     * Get online users (last seen within 5 minutes)
     * 
     * @param limit Maximum number of users to return
     * @return List of online users
     * @throws SQLException if database error occurs
     */
    public List<User> getOnlineUsers(int limit) throws SQLException {
        String sql = "SELECT * FROM users WHERE last_seen_at > DATE_SUB(NOW(), INTERVAL 5 MINUTE) ORDER BY last_seen_at DESC LIMIT ?";
        List<User> users = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        }

        return users;
    }

    /**
     * Delete user by ID
     * 
     * @param id User ID
     * @return true if deletion successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Check if email already exists
     * 
     * @param email Email to check
     * @return true if email exists, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    /**
     * Map ResultSet to User object
     * 
     * @param rs ResultSet
     * @return User object
     * @throws SQLException if database error occurs
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setEmailVerifiedAt(rs.getTimestamp("email_verified_at"));
        user.setPassword(rs.getString("password"));
        user.setRememberToken(rs.getString("remember_token"));
        user.setPoints(rs.getInt("points"));
        user.setRole(rs.getString("role"));
        user.setLastSeenAt(rs.getTimestamp("last_seen_at"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        return user;
    }

    /**
     * Get recent users (for admin dashboard)
     * 
     * @param limit number of users to retrieve
     * @return List of recent users
     */
    public List<User> getRecentUsers(int limit) throws SQLException {
        String sql = "SELECT * FROM users ORDER BY created_at DESC LIMIT ?";
        List<User> users = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        }

        return users;
    }
}
