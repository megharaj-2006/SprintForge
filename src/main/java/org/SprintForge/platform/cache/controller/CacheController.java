package org.SprintForge.platform.cache.controller;

import org.SprintForge.platform.cache.dto.CacheStatisticsResponse;
import org.SprintForge.platform.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("platformCacheController")
@RequestMapping("/cache")
public class CacheController {

    private final CacheService cacheService;

    @Autowired
    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping("/clear")
    public ResponseEntity<String> clearCache(@RequestParam(value = "group", required = false) String group) {
        cacheService.clear(group);
        return ResponseEntity.ok("Cache cleared successfully");
    }

    @PostMapping("/warm")
    public ResponseEntity<String> warmCache() {
        return ResponseEntity.ok("Cache warmup completed");
    }

    @GetMapping("/statistics")
    public ResponseEntity<CacheStatisticsResponse> getStatistics() {
        return ResponseEntity.ok(cacheService.getStatistics());
    }
}
