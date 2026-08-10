package org.SprintForge.platform.search.dto;

import java.util.List;

public class SaveSearchRequest {
    private String name;
    private String queryText;
    private String workspaceId;
    private String userId;
    private List<SearchFilterRule> filters;
    private boolean isShared;

    public SaveSearchRequest() {}

    public SaveSearchRequest(String name, String queryText, String workspaceId, String userId, List<SearchFilterRule> filters, boolean isShared) {
        this.name = name;
        this.queryText = queryText;
        this.workspaceId = workspaceId;
        this.userId = userId;
        this.filters = filters;
        this.isShared = isShared;
    }

    public static SaveSearchRequestBuilder builder() {
        return new SaveSearchRequestBuilder();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getQueryText() { return queryText; }
    public void setQueryText(String queryText) { this.queryText = queryText; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<SearchFilterRule> getFilters() { return filters; }
    public void setFilters(List<SearchFilterRule> filters) { this.filters = filters; }

    public boolean isShared() { return isShared; }
    public void setShared(boolean shared) { isShared = shared; }

    public static class SaveSearchRequestBuilder {
        private String name;
        private String queryText;
        private String workspaceId;
        private String userId;
        private List<SearchFilterRule> filters;
        private boolean isShared;

        public SaveSearchRequestBuilder name(String name) { this.name = name; return this; }
        public SaveSearchRequestBuilder queryText(String queryText) { this.queryText = queryText; return this; }
        public SaveSearchRequestBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public SaveSearchRequestBuilder userId(String userId) { this.userId = userId; return this; }
        public SaveSearchRequestBuilder filters(List<SearchFilterRule> filters) { this.filters = filters; return this; }
        public SaveSearchRequestBuilder isShared(boolean isShared) { this.isShared = isShared; return this; }

        public SaveSearchRequest build() {
            return new SaveSearchRequest(name, queryText, workspaceId, userId, filters, isShared);
        }
    }
}
