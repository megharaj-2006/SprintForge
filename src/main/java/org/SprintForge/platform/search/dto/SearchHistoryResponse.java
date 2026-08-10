package org.SprintForge.platform.search.dto;

import java.time.Instant;

public class SearchHistoryResponse {
    private Long id;
    private String userId;
    private String workspaceId;
    private String queryText;
    private int resultCount;
    private long executionTimeMs;
    private Instant searchedAt;

    public SearchHistoryResponse() {}

    public SearchHistoryResponse(Long id, String userId, String workspaceId, String queryText, int resultCount, long executionTimeMs, Instant searchedAt) {
        this.id = id;
        this.userId = userId;
        this.workspaceId = workspaceId;
        this.queryText = queryText;
        this.resultCount = resultCount;
        this.executionTimeMs = executionTimeMs;
        this.searchedAt = searchedAt;
    }

    public static SearchHistoryResponseBuilder builder() {
        return new SearchHistoryResponseBuilder();
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

    public static class SearchHistoryResponseBuilder {
        private Long id;
        private String userId;
        private String workspaceId;
        private String queryText;
        private int resultCount;
        private long executionTimeMs;
        private Instant searchedAt;

        public SearchHistoryResponseBuilder id(Long id) { this.id = id; return this; }
        public SearchHistoryResponseBuilder userId(String userId) { this.userId = userId; return this; }
        public SearchHistoryResponseBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public SearchHistoryResponseBuilder queryText(String queryText) { this.queryText = queryText; return this; }
        public SearchHistoryResponseBuilder resultCount(int resultCount) { this.resultCount = resultCount; return this; }
        public SearchHistoryResponseBuilder executionTimeMs(long executionTimeMs) { this.executionTimeMs = executionTimeMs; return this; }
        public SearchHistoryResponseBuilder searchedAt(Instant searchedAt) { this.searchedAt = searchedAt; return this; }

        public SearchHistoryResponse build() {
            return new SearchHistoryResponse(id, userId, workspaceId, queryText, resultCount, executionTimeMs, searchedAt);
        }
    }
}
