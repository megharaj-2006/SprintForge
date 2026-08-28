package org.SprintForge.platform.dev.controller;

import org.SprintForge.platform.dev.service.DeveloperUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController("platformDeveloperUtilityController")
@RequestMapping("/developer")
@Profile({"dev", "test"})
@PreAuthorize("hasRole('ADMIN')")
public class DeveloperUtilityController {

    private final DeveloperUtilityService developerUtilityService;

    @Autowired
    public DeveloperUtilityController(DeveloperUtilityService developerUtilityService) {
        this.developerUtilityService = developerUtilityService;
    }

    @PostMapping("/reindex")
    public ResponseEntity<Map<String, Object>> reindexSearch() {
        return ResponseEntity.ok(developerUtilityService.reindexSearch());
    }

    @PostMapping("/cache/clear")
    public ResponseEntity<Map<String, Object>> clearCache() {
        return ResponseEntity.ok(developerUtilityService.clearCache());
    }

    @PostMapping("/events/replay")
    public ResponseEntity<Map<String, Object>> replayEvents() {
        return ResponseEntity.ok(developerUtilityService.replayEvents());
    }

    @PostMapping("/jobs/retry")
    public ResponseEntity<Map<String, Object>> replayFailedJobs() {
        return ResponseEntity.ok(developerUtilityService.replayFailedJobs());
    }

    @PostMapping("/seed")
    public ResponseEntity<Map<String, Object>> seedDemoWorkspace() {
        return ResponseEntity.ok(developerUtilityService.seedDemoWorkspace());
    }
}
