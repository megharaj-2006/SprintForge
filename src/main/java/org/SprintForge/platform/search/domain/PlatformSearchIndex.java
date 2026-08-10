package org.SprintForge.platform.search.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformSearchIndex")
@Table(name = "platform_search_index")
public class PlatformSearchIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "keywords", columnDefinition = "TEXT")
    private String keywords;

    @Column(name = "last_indexed_at", nullable = false)
    private Instant lastIndexedAt = Instant.now();

    public PlatformSearchIndex() {}

    public PlatformSearchIndex(Long id, String entityType, String entityId, String workspaceId, String projectId, String title, String content, String keywords, Instant lastIndexedAt) {
        this.id = id;
        this.entityType = entityType;
        this.entityId = entityId;
        this.workspaceId = workspaceId;
        this.projectId = projectId;
        this.title = title;
        this.content = content;
        this.keywords = keywords;
        this.lastIndexedAt = lastIndexedAt != null ? lastIndexedAt : Instant.now();
    }

    public static PlatformSearchIndexBuilder builder() {
        return new PlatformSearchIndexBuilder();
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

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }

    public Instant getLastIndexedAt() { return lastIndexedAt; }
    public void setLastIndexedAt(Instant lastIndexedAt) { this.lastIndexedAt = lastIndexedAt; }

    public static class PlatformSearchIndexBuilder {
        private Long id;
        private String entityType;
        private String entityId;
        private String workspaceId;
        private String projectId;
        private String title;
        private String content;
        private String keywords;
        private Instant lastIndexedAt = Instant.now();

        public PlatformSearchIndexBuilder id(Long id) { this.id = id; return this; }
        public PlatformSearchIndexBuilder entityType(String entityType) { this.entityType = entityType; return this; }
        public PlatformSearchIndexBuilder entityId(String entityId) { this.entityId = entityId; return this; }
        public PlatformSearchIndexBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public PlatformSearchIndexBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public PlatformSearchIndexBuilder title(String title) { this.title = title; return this; }
        public PlatformSearchIndexBuilder content(String content) { this.content = content; return this; }
        public PlatformSearchIndexBuilder keywords(String keywords) { this.keywords = keywords; return this; }
        public PlatformSearchIndexBuilder lastIndexedAt(Instant lastIndexedAt) { this.lastIndexedAt = lastIndexedAt; return this; }

        public PlatformSearchIndex build() {
            return new PlatformSearchIndex(id, entityType, entityId, workspaceId, projectId, title, content, keywords, lastIndexedAt);
        }
    }
}
