package org.SprintForge.platform.cache.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformCacheEntry")
@Table(name = "platform_cache_entries")
public class CacheEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cache_key", unique = true, nullable = false)
    private String cacheKey;

    @Column(name = "cache_group", nullable = false)
    private String cacheGroup;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "last_accessed", nullable = false)
    private Instant lastAccessed = Instant.now();

    public CacheEntry() {}

    public CacheEntry(Long id, String cacheKey, String cacheGroup, Instant expiresAt, Instant createdAt, Instant lastAccessed) {
        this.id = id;
        this.cacheKey = cacheKey;
        this.cacheGroup = cacheGroup;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt != null ? createdAt : Instant.now();
        this.lastAccessed = lastAccessed != null ? lastAccessed : Instant.now();
    }

    public static CacheEntryBuilder builder() {
        return new CacheEntryBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCacheKey() { return cacheKey; }
    public void setCacheKey(String cacheKey) { this.cacheKey = cacheKey; }

    public String getCacheGroup() { return cacheGroup; }
    public void setCacheGroup(String cacheGroup) { this.cacheGroup = cacheGroup; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getLastAccessed() { return lastAccessed; }
    public void setLastAccessed(Instant lastAccessed) { this.lastAccessed = lastAccessed; }

    public static class CacheEntryBuilder {
        private Long id;
        private String cacheKey;
        private String cacheGroup;
        private Instant expiresAt;
        private Instant createdAt = Instant.now();
        private Instant lastAccessed = Instant.now();

        public CacheEntryBuilder id(Long id) { this.id = id; return this; }
        public CacheEntryBuilder cacheKey(String cacheKey) { this.cacheKey = cacheKey; return this; }
        public CacheEntryBuilder cacheGroup(String cacheGroup) { this.cacheGroup = cacheGroup; return this; }
        public CacheEntryBuilder expiresAt(Instant expiresAt) { this.expiresAt = expiresAt; return this; }
        public CacheEntryBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public CacheEntryBuilder lastAccessed(Instant lastAccessed) { this.lastAccessed = lastAccessed; return this; }

        public CacheEntry build() {
            return new CacheEntry(id, cacheKey, cacheGroup, expiresAt, createdAt, lastAccessed);
        }
    }
}
