package org.SprintForge.platform.cache.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.cache.domain.CacheEntry;
import org.SprintForge.platform.cache.dto.CacheStatisticsResponse;
import org.SprintForge.platform.cache.repository.PlatformCacheEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service("platformCacheService")
public class CacheService {

    private static final Logger log = LoggerFactory.getLogger(CacheService.class);
    private final PlatformCacheEntryRepository cacheEntryRepository;
    private final Map<String, Object> inMemoryStore = new ConcurrentHashMap<>();
    private final AtomicLong hits = new AtomicLong(0);
    private final AtomicLong misses = new AtomicLong(0);

    @Autowired
    public CacheService(PlatformCacheEntryRepository cacheEntryRepository) {
        this.cacheEntryRepository = cacheEntryRepository;
    }

    public Object get(String key) {
        Object val = inMemoryStore.get(key);
        if (val != null) {
            hits.incrementAndGet();
            return val;
        }
        misses.incrementAndGet();
        return null;
    }

    @Transactional
    public void put(String key, String group, Object value, long ttlSeconds) {
        inMemoryStore.put(key, value);

        Instant expiresAt = ttlSeconds > 0 ? Instant.now().plusSeconds(ttlSeconds) : null;
        CacheEntry entry = cacheEntryRepository.findByCacheKey(key)
                .orElseGet(() -> CacheEntry.builder().cacheKey(key).cacheGroup(group).build());

        entry.setCacheGroup(group);
        entry.setExpiresAt(expiresAt);
        entry.setLastAccessed(Instant.now());
        cacheEntryRepository.save(entry);
    }

    @Transactional
    public void clear(String group) {
        if (group != null && !group.isBlank()) {
            cacheEntryRepository.deleteByCacheGroup(group);
            inMemoryStore.clear();
            log.info("Cleared cache group: {}", group);
        } else {
            cacheEntryRepository.deleteAll();
            inMemoryStore.clear();
            log.info("Cleared all caches");
        }
    }

    public CacheStatisticsResponse getStatistics() {
        long total = cacheEntryRepository.count();
        long h = hits.get();
        long m = misses.get();
        double ratio = (h + m) > 0 ? ((double) h / (h + m)) * 100.0 : 100.0;

        return CacheStatisticsResponse.builder()
                .totalEntries(total)
                .cacheHits(h)
                .cacheMisses(m)
                .hitRatio(ratio)
                .build();
    }
}
