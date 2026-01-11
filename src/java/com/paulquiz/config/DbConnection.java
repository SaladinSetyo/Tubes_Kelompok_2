package com.paulquiz.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Manager using Singleton pattern
 * Handles database connections for the Paul Quiz application
 */
public class DbConnection {
    
    // Database configuration - UPDATE THESE VALUES
    private static final String DB_URL = "jdbc:mysql://localhost:3306/paulquiz";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Root123!";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static DbConnection instance;
    private Connection connection;
    
    /**
     * Private constructor to prevent instantiation
     */
    private DbConnection() {
        try {
            // Load MySQL JDBC driver
            Class.forName(DB_DRIVER);
            
            // Establish connection
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            System.out.println("Database connection established successfully!");
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection!");
            e.printStackTrace();
        }
    }
    
    /**
     * Get the singleton instance of DbConnection
     * @return DbConnection instance
     */
    public static DbConnection getInstance() {
        if (instance == null) {
            synchronized (DbConnection.class) {
                if (instance == null) {
                    instance = new DbConnection();
                }
            }
        }
        return instance;
    }
    
    /**
     * Get the database connection
     * @return Connection object
     * @throws SQLException if connection is closed or invalid
     */
    public Connection getConnection() throws SQLException {
        // Check if connection is still valid
        if (connection == null || connection.isClosed()) {
            // Reconnect if connection was closed
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }
    
    /**
     * Close the database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection!");
            e.printStackTrace();
        }
    }
    
    /**
     * Test database connection
     * @return true if connection is successful, false otherwise
     */
    public boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}

