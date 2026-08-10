package org.SprintForge.platform.search.dto;

import java.util.List;

public class GlobalSearchRequest {
    private String query;
    private String workspaceId;
    private String userId;
    private List<String> entityTypes;
    private int page = 0;
    private int size = 20;
    private boolean includeArchived = false;

    public GlobalSearchRequest() {}

    public GlobalSearchRequest(String query, String workspaceId, String userId, List<String> entityTypes, int page, int size, boolean includeArchived) {
        this.query = query;
        this.workspaceId = workspaceId;
        this.userId = userId;
        this.entityTypes = entityTypes;
        this.page = page;
        this.size = size;
        this.includeArchived = includeArchived;
    }

    public static GlobalSearchRequestBuilder builder() {
        return new GlobalSearchRequestBuilder();
    }

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<String> getEntityTypes() { return entityTypes; }
    public void setEntityTypes(List<String> entityTypes) { this.entityTypes = entityTypes; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public boolean isIncludeArchived() { return includeArchived; }
    public void setIncludeArchived(boolean includeArchived) { this.includeArchived = includeArchived; }

    public static class GlobalSearchRequestBuilder {
        private String query;
        private String workspaceId;
        private String userId;
        private List<String> entityTypes;
        private int page = 0;
        private int size = 20;
        private boolean includeArchived = false;

        public GlobalSearchRequestBuilder query(String query) { this.query = query; return this; }
        public GlobalSearchRequestBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public GlobalSearchRequestBuilder userId(String userId) { this.userId = userId; return this; }
        public GlobalSearchRequestBuilder entityTypes(List<String> entityTypes) { this.entityTypes = entityTypes; return this; }
        public GlobalSearchRequestBuilder page(int page) { this.page = page; return this; }
        public GlobalSearchRequestBuilder size(int size) { this.size = size; return this; }
        public GlobalSearchRequestBuilder includeArchived(boolean includeArchived) { this.includeArchived = includeArchived; return this; }

        public GlobalSearchRequest build() {
            return new GlobalSearchRequest(query, workspaceId, userId, entityTypes, page, size, includeArchived);
        }
    }
}
