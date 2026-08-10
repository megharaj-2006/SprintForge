package org.SprintForge.platform.search.dto;

import java.util.List;

public class SearchGroupResponse {
    private String entityType;
    private long totalCount;
    private List<SearchResultResponse> items;

    public SearchGroupResponse() {}

    public SearchGroupResponse(String entityType, long totalCount, List<SearchResultResponse> items) {
        this.entityType = entityType;
        this.totalCount = totalCount;
        this.items = items;
    }

    public static SearchGroupResponseBuilder builder() {
        return new SearchGroupResponseBuilder();
    }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public long getTotalCount() { return totalCount; }
    public void setTotalCount(long totalCount) { this.totalCount = totalCount; }

    public List<SearchResultResponse> getItems() { return items; }
    public void setItems(List<SearchResultResponse> items) { this.items = items; }

    public static class SearchGroupResponseBuilder {
        private String entityType;
        private long totalCount;
        private List<SearchResultResponse> items;

        public SearchGroupResponseBuilder entityType(String entityType) { this.entityType = entityType; return this; }
        public SearchGroupResponseBuilder totalCount(long totalCount) { this.totalCount = totalCount; return this; }
        public SearchGroupResponseBuilder items(List<SearchResultResponse> items) { this.items = items; return this; }

        public SearchGroupResponse build() {
            return new SearchGroupResponse(entityType, totalCount, items);
        }
    }
}
