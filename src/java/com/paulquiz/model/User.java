package com.paulquiz.model;

import java.sql.Timestamp;

/**
 * User entity class representing users table
 */
public class User {
    private Long id;
    private String name;
    private String email;
    private Timestamp emailVerifiedAt;
    private String password;
    private String rememberToken;
    private Integer points;
    private String role; // 'admin' or 'user'
    private Timestamp lastSeenAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Constructors
    public User() {
        this.points = 0;
        this.role = "user";
    }
    
    public User(String name, String email, String password) {
        this();
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Timestamp getEmailVerifiedAt() {
        return emailVerifiedAt;
    }
    
    public void setEmailVerifiedAt(Timestamp emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRememberToken() {
        return rememberToken;
    }
    
    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }
    
    public Integer getPoints() {
        return points;
    }
    
    public void setPoints(Integer points) {
        this.points = points;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public Timestamp getLastSeenAt() {
        return lastSeenAt;
    }
    
    public void setLastSeenAt(Timestamp lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
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
    
    // Helper methods
    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(this.role);
    }
    
    public boolean isOnline() {
        if (lastSeenAt == null) return false;
        
        // User is online if last seen within 5 minutes
        long fiveMinutesInMillis = 5 * 60 * 1000;
        long currentTime = System.currentTimeMillis();
        long lastSeenTime = lastSeenAt.getTime();
        
        return (currentTime - lastSeenTime) < fiveMinutesInMillis;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", points=" + points +
                ", role='" + role + '\'' +
                '}';
    }
}

