package com.paulquiz.dao;

import com.paulquiz.config.DbConnection;
import com.paulquiz.model.Content;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Content operations
 */
public class ContentDAO {

    private Connection getConnection() throws SQLException {
        return DbConnection.getInstance().getConnection();
    }

    /**
     * Create a new content
     * 
     * @param content Content object to create
     * @return Created content with ID set
     * @throws SQLException if database error occurs
     */
    public Content create(Content content) throws SQLException {
        String sql = "INSERT INTO contents (module_id, title, type, body, media_url, `order`, quiz_id, description, is_featured) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, content.getModuleId());
            stmt.setString(2, content.getTitle());
            stmt.setString(3, content.getType());
            stmt.setString(4, content.getBody());
            stmt.setString(5, content.getMediaUrl());
            stmt.setInt(6, content.getOrder());

            if (content.getQuizId() != null) {
                stmt.setLong(7, content.getQuizId());
            } else {
                stmt.setNull(7, Types.BIGINT);
            }

            stmt.setString(8, content.getDescription());
            stmt.setBoolean(9, content.getIsFeatured());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        content.setId(rs.getLong(1));
                    }
                }
            }

            return content;
        }
    }

    /**
     * Find content by ID
     * 
     * @param id Content ID
     * @return Content object or null if not found
     * @throws SQLException if database error occurs
     */
    public Content findById(Long id) throws SQLException {
        String sql = "SELECT c.*, m.title as module_name FROM contents c " +
                "LEFT JOIN modules m ON c.module_id = m.id " +
                "WHERE c.id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToContent(rs);
                }
            }
        }

        return null;
    }

    /**
     * Find conten by module ID
     * 
     * @param moduleId Module ID
     * @return List of contents
     * @throws SQLException if database error occurs
     */
    public List<Content> findByModule(Long moduleId) throws SQLException {
        String sql = "SELECT c.*, m.title as module_name FROM contents c " +
                "LEFT JOIN modules m ON c.module_id = m.id " +
                "WHERE c.module_id = ? ORDER BY c.`order` ASC";
        List<Content> contents = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, moduleId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    contents.add(mapResultSetToContent(rs));
                }
            }
        }

        return contents;
    }

    /**
     * Find contents by type
     * 
     * @param type  Content type ('article', 'video', 'infographic', 'quiz')
     * @param limit Maximum number of contents to return
     * @return List of contents
     * @throws SQLException if database error occurs
     */
    public List<Content> findByType(String type, int limit) throws SQLException {
        String sql = "SELECT c.*, m.title as module_name FROM contents c " +
                "LEFT JOIN modules m ON c.module_id = m.id " +
                "WHERE c.type = ? ORDER BY c.is_featured DESC, c.created_at DESC LIMIT ?";
        List<Content> contents = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, type);
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    contents.add(mapResultSetToContent(rs));
                }
            }
        }

        return contents;
    }

    /**
     * Update existing content
     * 
     * @param content Content object to update
     * @return true if update successful
     * @throws SQLException if database error occurs
     */
    public boolean update(Content content) throws SQLException {
        String sql = "UPDATE contents SET module_id = ?, title = ?, type = ?, body = ?, media_url = ?, `order` = ?, quiz_id = ?, description = ?, is_featured = ? WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, content.getModuleId());
            stmt.setString(2, content.getTitle());
            stmt.setString(3, content.getType());
            stmt.setString(4, content.getBody());
            stmt.setString(5, content.getMediaUrl());
            stmt.setInt(6, content.getOrder());

            if (content.getQuizId() != null) {
                stmt.setLong(7, content.getQuizId());
            } else {
                stmt.setNull(7, Types.BIGINT);
            }

            stmt.setString(8, content.getDescription());
            stmt.setBoolean(9, content.getIsFeatured());
            stmt.setLong(10, content.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Delete content by ID
     * 
     * @param id Content ID
     * @return true if delete successful
     * @throws SQLException if database error occurs
     */
    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM contents WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Find all contents
     * 
     * @return List of all contents
     * @throws SQLException if database error occurs
     */
    public List<Content> findAll() throws SQLException {
        String sql = "SELECT c.*, m.title as module_name FROM contents c " +
                "LEFT JOIN modules m ON c.module_id = m.id " +
                "ORDER BY c.module_id ASC, c.`order` ASC";
        List<Content> contents = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                contents.add(mapResultSetToContent(rs));
            }
        }

        return contents;
    }

    /**
     * Find featured quiz content
     * 
     * @return Featured quiz content or null if not found
     * @throws SQLException if database error occurs
     */
    public Content findFeaturedQuiz() throws SQLException {
        String sql = "SELECT c.*, m.title as module_name FROM contents c " +
                "LEFT JOIN modules m ON c.module_id = m.id " +
                "WHERE c.type = 'quiz' AND c.is_featured = TRUE " +
                "ORDER BY c.created_at DESC LIMIT 1";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return mapResultSetToContent(rs);
            }
        }

        return null;
    }

    /**
     * Find all featured contents
     * 
     * @param limit Maximum number of contents to return
     * @return List of featured contents
     * @throws SQLException if database error occurs
     */
    public List<Content> findFeatured(int limit) throws SQLException {
        String sql = "SELECT c.*, m.title as module_name FROM contents c " +
                "LEFT JOIN modules m ON c.module_id = m.id " +
                "WHERE c.is_featured = TRUE ORDER BY c.created_at DESC LIMIT ?";
        List<Content> contents = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    contents.add(mapResultSetToContent(rs));
                }
            }
        }

        return contents;
    }

    /**
     * Map ResultSet to Content object
     * 
     * @param rs ResultSet
     * @return Content object
     * @throws SQLException if database error occurs
     */
    private Content mapResultSetToContent(ResultSet rs) throws SQLException {
        Content content = new Content();
        content.setId(rs.getLong("id"));
        content.setModuleId(rs.getLong("module_id"));
        content.setTitle(rs.getString("title"));
        content.setType(rs.getString("type"));
        content.setBody(rs.getString("body"));
        content.setMediaUrl(rs.getString("media_url"));
        content.setOrder(rs.getInt("order"));

        long quizId = rs.getLong("quiz_id");
        if (!rs.wasNull()) {
            content.setQuizId(quizId);
        }

        content.setDescription(rs.getString("description"));
        content.setIsFeatured(rs.getBoolean("is_featured"));
        content.setCreatedAt(rs.getTimestamp("created_at"));
        content.setUpdatedAt(rs.getTimestamp("updated_at"));

        // Additional joined data
        content.setModuleName(rs.getString("module_name"));

        return content;
    }
}
