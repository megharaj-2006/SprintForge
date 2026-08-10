package org.SprintForge.platform.search.dto;

import java.util.List;
import java.util.Map;

public class SearchDashboardResponse {
    private long totalIndexedEntities;
    private long pendingReindexJobs;
    private long totalSearchesExecuted;
    private double averageSearchTimeMs;
    private Map<String, Long> indexedEntitiesByType;
    private List<String> popularSearches;

    public SearchDashboardResponse() {}

    public SearchDashboardResponse(long totalIndexedEntities, long pendingReindexJobs, long totalSearchesExecuted, double averageSearchTimeMs, Map<String, Long> indexedEntitiesByType, List<String> popularSearches) {
        this.totalIndexedEntities = totalIndexedEntities;
        this.pendingReindexJobs = pendingReindexJobs;
        this.totalSearchesExecuted = totalSearchesExecuted;
        this.averageSearchTimeMs = averageSearchTimeMs;
        this.indexedEntitiesByType = indexedEntitiesByType;
        this.popularSearches = popularSearches;
    }

    public static SearchDashboardResponseBuilder builder() {
        return new SearchDashboardResponseBuilder();
    }

    public long getTotalIndexedEntities() { return totalIndexedEntities; }
    public void setTotalIndexedEntities(long totalIndexedEntities) { this.totalIndexedEntities = totalIndexedEntities; }

    public long getPendingReindexJobs() { return pendingReindexJobs; }
    public void setPendingReindexJobs(long pendingReindexJobs) { this.pendingReindexJobs = pendingReindexJobs; }

    public long getTotalSearchesExecuted() { return totalSearchesExecuted; }
    public void setTotalSearchesExecuted(long totalSearchesExecuted) { this.totalSearchesExecuted = totalSearchesExecuted; }

    public double getAverageSearchTimeMs() { return averageSearchTimeMs; }
    public void setAverageSearchTimeMs(double averageSearchTimeMs) { this.averageSearchTimeMs = averageSearchTimeMs; }

    public Map<String, Long> getIndexedEntitiesByType() { return indexedEntitiesByType; }
    public void setIndexedEntitiesByType(Map<String, Long> indexedEntitiesByType) { this.indexedEntitiesByType = indexedEntitiesByType; }

    public List<String> getPopularSearches() { return popularSearches; }
    public void setPopularSearches(List<String> popularSearches) { this.popularSearches = popularSearches; }

    public static class SearchDashboardResponseBuilder {
        private long totalIndexedEntities;
        private long pendingReindexJobs;
        private long totalSearchesExecuted;
        private double averageSearchTimeMs;
        private Map<String, Long> indexedEntitiesByType;
        private List<String> popularSearches;

        public SearchDashboardResponseBuilder totalIndexedEntities(long totalIndexedEntities) { this.totalIndexedEntities = totalIndexedEntities; return this; }
        public SearchDashboardResponseBuilder pendingReindexJobs(long pendingReindexJobs) { this.pendingReindexJobs = pendingReindexJobs; return this; }
        public SearchDashboardResponseBuilder totalSearchesExecuted(long totalSearchesExecuted) { this.totalSearchesExecuted = totalSearchesExecuted; return this; }
        public SearchDashboardResponseBuilder averageSearchTimeMs(double averageSearchTimeMs) { this.averageSearchTimeMs = averageSearchTimeMs; return this; }
        public SearchDashboardResponseBuilder indexedEntitiesByType(Map<String, Long> indexedEntitiesByType) { this.indexedEntitiesByType = indexedEntitiesByType; return this; }
        public SearchDashboardResponseBuilder popularSearches(List<String> popularSearches) { this.popularSearches = popularSearches; return this; }

        public SearchDashboardResponse build() {
            return new SearchDashboardResponse(totalIndexedEntities, pendingReindexJobs, totalSearchesExecuted, averageSearchTimeMs, indexedEntitiesByType, popularSearches);
        }
    }
}
