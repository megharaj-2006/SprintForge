package org.SprintForge.platform.search.controller;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.search.service.SearchRebuildService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("platformSearchIndexController")
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchIndexController {

    private final SearchRebuildService searchRebuildService;

    @PostMapping("/reindex")
    public ResponseEntity<Map<String, Object>> reindexAll() {
        long count = searchRebuildService.rebuildFullIndex();
        return ResponseEntity.ok(Map.of("message", "Full index rebuild completed", "count", count));
    }

    @PostMapping("/reindex/{entity}")
    public ResponseEntity<Map<String, Object>> reindexEntity(@PathVariable("entity") String entity) {
        long count = searchRebuildService.rebuildEntityIndex(entity);
        return ResponseEntity.ok(Map.of("message", "Rebuild completed for entity " + entity, "count", count));
    }

    @GetMapping("/index/status")
    public ResponseEntity<Map<String, String>> getIndexStatus() {
        return ResponseEntity.ok(Map.of("status", searchRebuildService.getIndexStatus()));
    }
}
