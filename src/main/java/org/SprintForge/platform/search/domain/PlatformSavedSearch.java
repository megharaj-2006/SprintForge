package org.SprintForge.platform.search.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformSavedSearch")
@Table(name = "platform_saved_searches")
public class PlatformSavedSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "query_text")
    private String queryText;

    @Column(name = "filters", columnDefinition = "TEXT")
    private String filters;

    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "is_shared", nullable = false)
    private boolean isShared;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    public PlatformSavedSearch() {}

    public PlatformSavedSearch(Long id, String name, String queryText, String filters, String workspaceId, String userId, boolean isShared, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.queryText = queryText;
        this.filters = filters;
        this.workspaceId = workspaceId;
        this.userId = userId;
        this.isShared = isShared;
        this.createdAt = createdAt != null ? createdAt : Instant.now();
    }

    public static PlatformSavedSearchBuilder builder() {
        return new PlatformSavedSearchBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getQueryText() { return queryText; }
    public void setQueryText(String queryText) { this.queryText = queryText; }

    public String getFilters() { return filters; }
    public void setFilters(String filters) { this.filters = filters; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public boolean isShared() { return isShared; }
    public void setShared(boolean shared) { isShared = shared; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public static class PlatformSavedSearchBuilder {
        private Long id;
        private String name;
        private String queryText;
        private String filters;
        private String workspaceId;
        private String userId;
        private boolean isShared;
        private Instant createdAt = Instant.now();

        public PlatformSavedSearchBuilder id(Long id) { this.id = id; return this; }
        public PlatformSavedSearchBuilder name(String name) { this.name = name; return this; }
        public PlatformSavedSearchBuilder queryText(String queryText) { this.queryText = queryText; return this; }
        public PlatformSavedSearchBuilder filters(String filters) { this.filters = filters; return this; }
        public PlatformSavedSearchBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public PlatformSavedSearchBuilder userId(String userId) { this.userId = userId; return this; }
        public PlatformSavedSearchBuilder isShared(boolean isShared) { this.isShared = isShared; return this; }
        public PlatformSavedSearchBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }

        public PlatformSavedSearch build() {
            return new PlatformSavedSearch(id, name, queryText, filters, workspaceId, userId, isShared, createdAt);
        }
    }
}
