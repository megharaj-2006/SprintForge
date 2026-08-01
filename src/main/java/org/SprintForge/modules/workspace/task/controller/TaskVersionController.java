package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.TaskVersion;
import org.SprintForge.modules.workspace.task.service.TaskVersionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Versioning Controller", description = "REST endpoints for task snapshot versioning, diff, and rollback")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TaskVersionController {

    private final TaskVersionService versionService;

    @Operation(summary = "Get task version history")
    @GetMapping("/tasks/{id}/versions")
    public ResponseEntity<List<TaskVersion>> listVersions(@PathVariable Long id) {
        return ResponseEntity.ok(versionService.listVersions(id));
    }

    @Operation(summary = "Create task version snapshot")
    @PostMapping("/tasks/{id}/versions")
    public ResponseEntity<TaskVersion> createVersion(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(versionService.createVersion(id, actorId));
    }

    @Operation(summary = "Restore task to a specific version")
    @PostMapping("/versions/{id}/restore")
    public ResponseEntity<TaskVersion> restoreVersion(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(versionService.restoreVersion(id, actorId));
    }

    @Operation(summary = "Get diff between task versions")
    @GetMapping("/versions/{id}/diff")
    public ResponseEntity<Map<String, Object>> getDiff(@PathVariable Long id) {
        return ResponseEntity.ok(versionService.getDiff(id));
    }
}
