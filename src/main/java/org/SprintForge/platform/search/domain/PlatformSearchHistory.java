package org.SprintForge.platform.search.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformSearchHistory")
@Table(name = "platform_search_history")
public class PlatformSearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "query_text", nullable = false)
    private String queryText;

    @Column(name = "result_count", nullable = false)
    private int resultCount;

    @Column(name = "execution_time_ms", nullable = false)
    private long executionTimeMs;

    @Column(name = "searched_at", nullable = false)
    private Instant searchedAt = Instant.now();

    public PlatformSearchHistory() {}

    public PlatformSearchHistory(Long id, String userId, String workspaceId, String queryText, int resultCount, long executionTimeMs, Instant searchedAt) {
        this.id = id;
        this.userId = userId;
        this.workspaceId = workspaceId;
        this.queryText = queryText;
        this.resultCount = resultCount;
        this.executionTimeMs = executionTimeMs;
        this.searchedAt = searchedAt != null ? searchedAt : Instant.now();
    }

    public static PlatformSearchHistoryBuilder builder() {
        return new PlatformSearchHistoryBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public String getQueryText() { return queryText; }
    public void setQueryText(String queryText) { this.queryText = queryText; }

    public int getResultCount() { return resultCount; }
    public void setResultCount(int resultCount) { this.resultCount = resultCount; }

    public long getExecutionTimeMs() { return executionTimeMs; }
    public void setExecutionTimeMs(long executionTimeMs) { this.executionTimeMs = executionTimeMs; }

    public Instant getSearchedAt() { return searchedAt; }
    public void setSearchedAt(Instant searchedAt) { this.searchedAt = searchedAt; }

    public static class PlatformSearchHistoryBuilder {
        private Long id;
        private String userId;
        private String workspaceId;
        private String queryText;
        private int resultCount;
        private long executionTimeMs;
        private Instant searchedAt = Instant.now();

        public PlatformSearchHistoryBuilder id(Long id) { this.id = id; return this; }
        public PlatformSearchHistoryBuilder userId(String userId) { this.userId = userId; return this; }
        public PlatformSearchHistoryBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public PlatformSearchHistoryBuilder queryText(String queryText) { this.queryText = queryText; return this; }
        public PlatformSearchHistoryBuilder resultCount(int resultCount) { this.resultCount = resultCount; return this; }
        public PlatformSearchHistoryBuilder executionTimeMs(long executionTimeMs) { this.executionTimeMs = executionTimeMs; return this; }
        public PlatformSearchHistoryBuilder searchedAt(Instant searchedAt) { this.searchedAt = searchedAt; return this; }

        public PlatformSearchHistory build() {
            return new PlatformSearchHistory(id, userId, workspaceId, queryText, resultCount, executionTimeMs, searchedAt);
        }
    }
}
