package org.SprintForge.platform.search.dto;

import java.time.Instant;

public class SavedSearchResponse {
    private Long id;
    private String name;
    private String queryText;
    private String filters;
    private boolean isShared;
    private Instant createdAt;

    public SavedSearchResponse() {}

    public SavedSearchResponse(Long id, String name, String queryText, String filters, boolean isShared, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.queryText = queryText;
        this.filters = filters;
        this.isShared = isShared;
        this.createdAt = createdAt;
    }

    public static SavedSearchResponseBuilder builder() {
        return new SavedSearchResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getQueryText() { return queryText; }
    public void setQueryText(String queryText) { this.queryText = queryText; }

    public String getFilters() { return filters; }
    public void setFilters(String filters) { this.filters = filters; }

    public boolean isShared() { return isShared; }
    public void setShared(boolean shared) { isShared = shared; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public static class SavedSearchResponseBuilder {
        private Long id;
        private String name;
        private String queryText;
        private String filters;
        private boolean isShared;
        private Instant createdAt;

        public SavedSearchResponseBuilder id(Long id) { this.id = id; return this; }
        public SavedSearchResponseBuilder name(String name) { this.name = name; return this; }
        public SavedSearchResponseBuilder queryText(String queryText) { this.queryText = queryText; return this; }
        public SavedSearchResponseBuilder filters(String filters) { this.filters = filters; return this; }
        public SavedSearchResponseBuilder isShared(boolean isShared) { this.isShared = isShared; return this; }
        public SavedSearchResponseBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }

        public SavedSearchResponse build() {
            return new SavedSearchResponse(id, name, queryText, filters, isShared, createdAt);
        }
    }
}
