package org.SprintForge.platform.featureflag.controller;

import org.SprintForge.platform.featureflag.dto.FeatureFlagResponse;
import org.SprintForge.platform.featureflag.service.FeatureFlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformFeatureFlagController")
@RequestMapping("/feature-flags")
@PreAuthorize("hasRole('ADMIN')")
public class FeatureFlagController {

    private final FeatureFlagService featureFlagService;

    @Autowired
    public FeatureFlagController(FeatureFlagService featureFlagService) {
        this.featureFlagService = featureFlagService;
    }

    @GetMapping
    public ResponseEntity<List<FeatureFlagResponse>> getAllFlags() {
        return ResponseEntity.ok(featureFlagService.getAllFlags());
    }

    @PostMapping
    public ResponseEntity<FeatureFlagResponse> createOrUpdateFlag(
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "enabled", defaultValue = "true") boolean enabled,
            @RequestParam(value = "scope", defaultValue = "GLOBAL") String scope,
            @RequestParam(value = "workspaceId", required = false) String workspaceId,
            @RequestParam(value = "rolloutPercentage", defaultValue = "100") int rolloutPercentage) {
        return ResponseEntity.ok(featureFlagService.createOrUpdateFlag(name, description, enabled, scope, workspaceId, rolloutPercentage));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FeatureFlagResponse> toggleFlag(
            @PathVariable("id") Long id,
            @RequestParam("enabled") boolean enabled) {
        return ResponseEntity.ok(featureFlagService.toggleFlag(id, enabled));
    }

    @GetMapping("/check/{name}")
    public ResponseEntity<Boolean> checkFlag(@PathVariable("name") String name) {
        return ResponseEntity.ok(featureFlagService.isEnabled(name));
    }
}
