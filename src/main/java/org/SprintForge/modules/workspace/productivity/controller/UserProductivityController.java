package org.SprintForge.modules.workspace.productivity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.productivity.entity.RecentlyViewed;
import org.SprintForge.modules.workspace.productivity.service.RecentlyViewedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Productivity Controller", description = "REST endpoints for recently viewed items and personal productivity tracking")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserProductivityController {

    private final RecentlyViewedService recentlyViewedService;

    @Operation(summary = "Record a recently viewed item")
    @PostMapping("/recent")
    public ResponseEntity<RecentlyViewed> recordView(
            @RequestParam String entityType,
            @RequestParam Long entityId,
            @RequestParam(required = false) String title,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        RecentlyViewed response = recentlyViewedService.recordView(actorId, entityType, entityId, title);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get list of recently viewed items")
    @GetMapping("/recent")
    public ResponseEntity<List<RecentlyViewed>> getRecent(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(recentlyViewedService.getRecentlyViewed(actorId));
    }
}
