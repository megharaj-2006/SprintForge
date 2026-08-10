package org.SprintForge.platform.search.dto;

import java.util.List;

public class AdvancedSearchRequest {
    private String query;
    private String workspaceId;
    private String projectId;
    private String matchMode;
    private List<SearchFilterRule> filters;
    private String sortBy;
    private int page = 0;
    private int size = 20;

    public AdvancedSearchRequest() {}

    public AdvancedSearchRequest(String query, String workspaceId, String projectId, String matchMode, List<SearchFilterRule> filters, String sortBy, int page, int size) {
        this.query = query;
        this.workspaceId = workspaceId;
        this.projectId = projectId;
        this.matchMode = matchMode;
        this.filters = filters;
        this.sortBy = sortBy;
        this.page = page;
        this.size = size;
    }

    public static AdvancedSearchRequestBuilder builder() {
        return new AdvancedSearchRequestBuilder();
    }

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getMatchMode() { return matchMode; }
    public void setMatchMode(String matchMode) { this.matchMode = matchMode; }

    public List<SearchFilterRule> getFilters() { return filters; }
    public void setFilters(List<SearchFilterRule> filters) { this.filters = filters; }

    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public static class AdvancedSearchRequestBuilder {
        private String query;
        private String workspaceId;
        private String projectId;
        private String matchMode;
        private List<SearchFilterRule> filters;
        private String sortBy;
        private int page = 0;
        private int size = 20;

        public AdvancedSearchRequestBuilder query(String query) { this.query = query; return this; }
        public AdvancedSearchRequestBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public AdvancedSearchRequestBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public AdvancedSearchRequestBuilder matchMode(String matchMode) { this.matchMode = matchMode; return this; }
        public AdvancedSearchRequestBuilder filters(List<SearchFilterRule> filters) { this.filters = filters; return this; }
        public AdvancedSearchRequestBuilder sortBy(String sortBy) { this.sortBy = sortBy; return this; }
        public AdvancedSearchRequestBuilder page(int page) { this.page = page; return this; }
        public AdvancedSearchRequestBuilder size(int size) { this.size = size; return this; }

        public AdvancedSearchRequest build() {
            return new AdvancedSearchRequest(query, workspaceId, projectId, matchMode, filters, sortBy, page, size);
        }
    }
}
