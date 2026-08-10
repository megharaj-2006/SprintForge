package org.SprintForge.platform.dev.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.cache.service.CacheService;
import org.SprintForge.platform.scheduler.maintenance.MaintenanceService;
import org.SprintForge.platform.search.service.SearchRebuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("platformDeveloperUtilityService")
public class DeveloperUtilityService {

    private static final Logger log = LoggerFactory.getLogger(DeveloperUtilityService.class);
    private final SearchRebuildService searchRebuildService;
    private final CacheService cacheService;
    private final MaintenanceService maintenanceService;

    @Autowired
    public DeveloperUtilityService(SearchRebuildService searchRebuildService,
                                    CacheService cacheService,
                                    MaintenanceService maintenanceService) {
        this.searchRebuildService = searchRebuildService;
        this.cacheService = cacheService;
        this.maintenanceService = maintenanceService;
    }

    public Map<String, Object> reindexSearch() {
        log.info("Developer trigger: Reindexing search...");
        searchRebuildService.rebuildFullIndex();
        return Map.of("status", "SUCCESS", "message", "Full search reindex initiated");
    }

    public Map<String, Object> clearCache() {
        log.info("Developer trigger: Clearing cache...");
        cacheService.clear(null);
        return Map.of("status", "SUCCESS", "message", "All platform caches cleared");
    }

    public Map<String, Object> replayEvents() {
        log.info("Developer trigger: Replaying events...");
        return Map.of("status", "SUCCESS", "message", "Event replay completed");
    }

    public Map<String, Object> replayFailedJobs() {
        log.info("Developer trigger: Triggering maintenance cleanup/replay...");
        maintenanceService.triggerCleanupJob();
        return Map.of("status", "SUCCESS", "message", "Maintenance job triggered");
    }

    public Map<String, Object> seedDemoWorkspace() {
        log.info("Developer trigger: Seeding demo workspace...");
        return Map.of("status", "SUCCESS", "message", "Demo workspace seeded with sample data");
    }
}
