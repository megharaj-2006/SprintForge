package org.SprintForge.platform.search.dto;

import java.util.List;

public class SearchSuggestionResponse {
    private String prefix;
    private List<String> suggestions;

    public SearchSuggestionResponse() {}

    public SearchSuggestionResponse(String prefix, List<String> suggestions) {
        this.prefix = prefix;
        this.suggestions = suggestions;
    }

    public static SearchSuggestionResponseBuilder builder() {
        return new SearchSuggestionResponseBuilder();
    }

    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }

    public List<String> getSuggestions() { return suggestions; }
    public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }

    public static class SearchSuggestionResponseBuilder {
        private String prefix;
        private List<String> suggestions;

        public SearchSuggestionResponseBuilder prefix(String prefix) { this.prefix = prefix; return this; }
        public SearchSuggestionResponseBuilder suggestions(List<String> suggestions) { this.suggestions = suggestions; return this; }

        public SearchSuggestionResponse build() {
            return new SearchSuggestionResponse(prefix, suggestions);
        }
    }
}
