package org.SprintForge.platform.cache.dto;

public class CacheStatisticsResponse {
    private long totalEntries;
    private long cacheHits;
    private long cacheMisses;
    private double hitRatio;

    public CacheStatisticsResponse() {}

    public CacheStatisticsResponse(long totalEntries, long cacheHits, long cacheMisses, double hitRatio) {
        this.totalEntries = totalEntries;
        this.cacheHits = cacheHits;
        this.cacheMisses = cacheMisses;
        this.hitRatio = hitRatio;
    }

    public static CacheStatisticsResponseBuilder builder() {
        return new CacheStatisticsResponseBuilder();
    }

    public long getTotalEntries() { return totalEntries; }
    public void setTotalEntries(long totalEntries) { this.totalEntries = totalEntries; }

    public long getCacheHits() { return cacheHits; }
    public void setCacheHits(long cacheHits) { this.cacheHits = cacheHits; }

    public long getCacheMisses() { return cacheMisses; }
    public void setCacheMisses(long cacheMisses) { this.cacheMisses = cacheMisses; }

    public double getHitRatio() { return hitRatio; }
    public void setHitRatio(double hitRatio) { this.hitRatio = hitRatio; }

    public static class CacheStatisticsResponseBuilder {
        private long totalEntries;
        private long cacheHits;
        private long cacheMisses;
        private double hitRatio;

        public CacheStatisticsResponseBuilder totalEntries(long totalEntries) { this.totalEntries = totalEntries; return this; }
        public CacheStatisticsResponseBuilder cacheHits(long cacheHits) { this.cacheHits = cacheHits; return this; }
        public CacheStatisticsResponseBuilder cacheMisses(long cacheMisses) { this.cacheMisses = cacheMisses; return this; }
        public CacheStatisticsResponseBuilder hitRatio(double hitRatio) { this.hitRatio = hitRatio; return this; }

        public CacheStatisticsResponse build() {
            return new CacheStatisticsResponse(totalEntries, cacheHits, cacheMisses, hitRatio);
        }
    }
}
