package com.paulquiz.dao;

import com.paulquiz.config.DbConnection;
import com.paulquiz.model.Module;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Module operations
 */
public class ModuleDAO {

    private Connection getConnection() throws SQLException {
        return DbConnection.getInstance().getConnection();
    }

    /**
     * Create a new module
     * 
     * @param module Module object to create
     * @return Created module with ID set
     * @throws SQLException if database error occurs
     */
    public Module save(Module module) throws SQLException {
        String sql = "INSERT INTO modules (title, description) VALUES (?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, module.getTitle());
            stmt.setString(2, module.getDescription());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        module.setId(rs.getLong(1));
                    }
                }
            }

            return module;
        }
    }

    /**
     * Find module by ID
     * 
     * @param id Module ID
     * @return Module object or null if not found
     * @throws SQLException if database error occurs
     */
    public Module findById(Long id) throws SQLException {
        String sql = "SELECT * FROM modules WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToModule(rs);
                }
            }
        }

        return null;
    }

    /**
     * Find all modules
     * 
     * @return List of all modules
     * @throws SQLException if database error occurs
     */
    public List<Module> findAll() throws SQLException {
        String sql = "SELECT * FROM modules ORDER BY id ASC";
        List<Module> modules = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                modules.add(mapResultSetToModule(rs));
            }
        }

        return modules;
    }

    /**
     * Get first module (used for "Mulai Belajar" button on homepage)
     * 
     * @return First module or null if no modules exist
     * @throws SQLException if database error occurs
     */
    public Module getFirstModule() throws SQLException {
        String sql = "SELECT * FROM modules ORDER BY id ASC LIMIT 1";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return mapResultSetToModule(rs);
            }
        }

        return null;
    }

    /**
     * Update module
     * 
     * @param module Module object with updated data
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean update(Module module) throws SQLException {
        String sql = "UPDATE modules SET title = ?, description = ? WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, module.getTitle());
            stmt.setString(2, module.getDescription());
            stmt.setLong(3, module.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Delete module by ID
     * 
     * @param id Module ID
     * @return true if deletion successful, false otherwise
     * @throws SQLException if database error occurs
     */
    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM modules WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    /**
     * Map ResultSet to Module object
     * 
     * @param rs ResultSet
     * @return Module object
     * @throws SQLException if database error occurs
     */
    private Module mapResultSetToModule(ResultSet rs) throws SQLException {
        Module module = new Module();
        module.setId(rs.getLong("id"));
        module.setTitle(rs.getString("title"));
        module.setDescription(rs.getString("description"));
        module.setLevel(rs.getString("level"));
        module.setDurationMinutes(rs.getInt("duration_minutes"));
        module.setPoints(rs.getInt("points"));
        module.setIcon(rs.getString("icon"));
        module.setOrderIndex(rs.getInt("order_index"));
        module.setCreatedAt(rs.getTimestamp("created_at"));
        module.setUpdatedAt(rs.getTimestamp("updated_at"));
        return module;
    }
}
