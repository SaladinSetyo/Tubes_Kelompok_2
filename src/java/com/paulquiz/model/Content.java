package com.paulquiz.model;

import java.sql.Timestamp;

/**
 * Content entity class representing contents table
 */
public class Content {
    private Long id;
    private Long moduleId;
    private String title;
    private String type; // 'article', 'video', 'infographic', 'quiz'
    private String body;
    private String mediaUrl;
    private Integer order;
    private Long quizId;
    private String description;
    private Boolean isFeatured;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // For joined queries
    private String moduleName;
    private String quizTitle;
    
    // Constructors
    public Content() {
        this.order = 0;
        this.isFeatured = false;
    }
    
    public Content(Long moduleId, String title, String type) {
        this();
        this.moduleId = moduleId;
        this.title = title;
        this.type = type;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getModuleId() {
        return moduleId;
    }
    
    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public String getMediaUrl() {
        return mediaUrl;
    }
    
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
    
    public Integer getOrder() {
        return order;
    }
    
    public void setOrder(Integer order) {
        this.order = order;
    }
    
    public Long getQuizId() {
        return quizId;
    }
    
    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean getIsFeatured() {
        return isFeatured;
    }
    
    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
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
    
    public String getModuleName() {
        return moduleName;
    }
    
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    
    public String getQuizTitle() {
        return quizTitle;
    }
    
    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }
    
    // Helper methods
    public boolean isArticle() {
        return "article".equalsIgnoreCase(this.type);
    }
    
    public boolean isVideo() {
        return "video".equalsIgnoreCase(this.type);
    }
    
    public boolean isInfographic() {
        return "infographic".equalsIgnoreCase(this.type);
    }
    
    public boolean isQuiz() {
        return "quiz".equalsIgnoreCase(this.type);
    }
    
    public String getEmbedUrl() {
        if (!isVideo() || mediaUrl == null) return mediaUrl;
        
        // Convert YouTube watch URL to embed URL if needed
        if (mediaUrl.contains("youtube.com/watch?v=")) {
            String videoId = mediaUrl.substring(mediaUrl.indexOf("v=") + 2);
            if (videoId.contains("&")) {
                videoId = videoId.substring(0, videoId.indexOf("&"));
            }
            return "https://www.youtube.com/embed/" + videoId;
        } else if (mediaUrl.contains("youtu.be/")) {
            String videoId = mediaUrl.substring(mediaUrl.lastIndexOf("/") + 1);
            if (videoId.contains("?")) {
                videoId = videoId.substring(0, videoId.indexOf("?"));
            }
            return "https://www.youtube.com/embed/" + videoId;
        }
        
        return mediaUrl;
    }
    
    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", moduleId=" + moduleId +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", isFeatured=" + isFeatured +
                '}';
    }
}

