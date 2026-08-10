package org.SprintForge.platform.cache.repository;

import org.SprintForge.platform.cache.domain.CacheEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("platformCacheEntryRepository")
public interface PlatformCacheEntryRepository extends JpaRepository<CacheEntry, Long> {

    Optional<CacheEntry> findByCacheKey(String cacheKey);

    @Modifying
    void deleteByCacheGroup(String cacheGroup);
}
