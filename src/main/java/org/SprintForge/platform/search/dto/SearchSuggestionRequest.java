package org.SprintForge.platform.search.dto;

public class SearchSuggestionRequest {
    private String prefix;
    private String workspaceId;
    private int limit = 5;

    public SearchSuggestionRequest() {}

    public SearchSuggestionRequest(String prefix, String workspaceId, int limit) {
        this.prefix = prefix;
        this.workspaceId = workspaceId;
        this.limit = limit;
    }

    public static SearchSuggestionRequestBuilder builder() {
        return new SearchSuggestionRequestBuilder();
    }

    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    public static class SearchSuggestionRequestBuilder {
        private String prefix;
        private String workspaceId;
        private int limit = 5;

        public SearchSuggestionRequestBuilder prefix(String prefix) { this.prefix = prefix; return this; }
        public SearchSuggestionRequestBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public SearchSuggestionRequestBuilder limit(int limit) { this.limit = limit; return this; }

        public SearchSuggestionRequest build() {
            return new SearchSuggestionRequest(prefix, workspaceId, limit);
        }
    }
}
