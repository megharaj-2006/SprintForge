package org.SprintForge.platform.search.dto;

import java.time.Instant;

public class SearchResultResponse {
    private Long id;
    private String entityType;
    private String entityId;
    private String workspaceId;
    private String projectId;
    private String title;
    private String contentSnippet;
    private String highlightedTitle;
    private double relevanceScore;
    private Instant lastIndexedAt;

    public SearchResultResponse() {}

    public SearchResultResponse(Long id, String entityType, String entityId, String workspaceId, String projectId, String title, String contentSnippet, String highlightedTitle, double relevanceScore, Instant lastIndexedAt) {
        this.id = id;
        this.entityType = entityType;
        this.entityId = entityId;
        this.workspaceId = workspaceId;
        this.projectId = projectId;
        this.title = title;
        this.contentSnippet = contentSnippet;
        this.highlightedTitle = highlightedTitle;
        this.relevanceScore = relevanceScore;
        this.lastIndexedAt = lastIndexedAt;
    }

    public static SearchResultResponseBuilder builder() {
        return new SearchResultResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContentSnippet() { return contentSnippet; }
    public void setContentSnippet(String contentSnippet) { this.contentSnippet = contentSnippet; }

    public String getHighlightedTitle() { return highlightedTitle; }
    public void setHighlightedTitle(String highlightedTitle) { this.highlightedTitle = highlightedTitle; }

    public double getRelevanceScore() { return relevanceScore; }
    public void setRelevanceScore(double relevanceScore) { this.relevanceScore = relevanceScore; }

    public Instant getLastIndexedAt() { return lastIndexedAt; }
    public void setLastIndexedAt(Instant lastIndexedAt) { this.lastIndexedAt = lastIndexedAt; }

    public static class SearchResultResponseBuilder {
        private Long id;
        private String entityType;
        private String entityId;
        private String workspaceId;
        private String projectId;
        private String title;
        private String contentSnippet;
        private String highlightedTitle;
        private double relevanceScore;
        private Instant lastIndexedAt;

        public SearchResultResponseBuilder id(Long id) { this.id = id; return this; }
        public SearchResultResponseBuilder entityType(String entityType) { this.entityType = entityType; return this; }
        public SearchResultResponseBuilder entityId(String entityId) { this.entityId = entityId; return this; }
        public SearchResultResponseBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public SearchResultResponseBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public SearchResultResponseBuilder title(String title) { this.title = title; return this; }
        public SearchResultResponseBuilder contentSnippet(String contentSnippet) { this.contentSnippet = contentSnippet; return this; }
        public SearchResultResponseBuilder highlightedTitle(String highlightedTitle) { this.highlightedTitle = highlightedTitle; return this; }
        public SearchResultResponseBuilder relevanceScore(double relevanceScore) { this.relevanceScore = relevanceScore; return this; }
        public SearchResultResponseBuilder lastIndexedAt(Instant lastIndexedAt) { this.lastIndexedAt = lastIndexedAt; return this; }

        public SearchResultResponse build() {
            return new SearchResultResponse(id, entityType, entityId, workspaceId, projectId, title, contentSnippet, highlightedTitle, relevanceScore, lastIndexedAt);
        }
    }
}
