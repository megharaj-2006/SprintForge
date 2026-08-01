package org.SprintForge.modules.workspace.activity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.activity.entity.ActivityFeed;
import org.SprintForge.modules.workspace.activity.service.ActivityFeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Activity Feed Controller", description = "REST endpoints for real-time collaboration timeline feeds")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ActivityFeedController {

    private final ActivityFeedService activityFeedService;

    @Operation(summary = "Get global activity timeline")
    @GetMapping("/activity")
    public ResponseEntity<List<ActivityFeed>> getGlobalActivity(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(activityFeedService.getGlobalActivity(page, size));
    }

    @Operation(summary = "Get project activity feed")
    @GetMapping("/projects/{id}/activity")
    public ResponseEntity<List<ActivityFeed>> getProjectActivity(@PathVariable Long id) {
        return ResponseEntity.ok(activityFeedService.getProjectActivity(id));
    }

    @Operation(summary = "Get task activity timeline")
    @GetMapping("/tasks/{id}/activity")
    public ResponseEntity<List<ActivityFeed>> getTaskActivity(@PathVariable Long id) {
        return ResponseEntity.ok(activityFeedService.getTaskActivity(id));
    }

    @Operation(summary = "Get user activity feed")
    @GetMapping("/users/{id}/activity")
    public ResponseEntity<List<ActivityFeed>> getUserActivity(@PathVariable Long id) {
        return ResponseEntity.ok(activityFeedService.getUserActivity(id));
    }
}
