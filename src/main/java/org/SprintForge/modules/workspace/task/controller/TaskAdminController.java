package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.service.MaintenanceService;
import org.SprintForge.modules.workspace.task.service.TaskAdministrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Administration Controller", description = "REST endpoints for system maintenance, indexing, and governance operations")
@PreAuthorize("hasRole('ADMIN')")
public class TaskAdminController {

    private final TaskAdministrationService adminService;
    private final MaintenanceService maintenanceService;

    @Operation(summary = "Reindex all tasks in search engine")
    @PostMapping("/reindex")
    public ResponseEntity<Map<String, Object>> reindexTasks() {
        return ResponseEntity.ok(adminService.reindexTasks());
    }

    @Operation(summary = "Cleanup expired trash items")
    @PostMapping("/cleanup")
    public ResponseEntity<Map<String, Object>> cleanupTrash() {
        return ResponseEntity.ok(adminService.cleanupTrash());
    }

    @Operation(summary = "Recalculate story points and metrics")
    @PostMapping("/recalculate")
    public ResponseEntity<Map<String, Object>> recalculateMetrics() {
        return ResponseEntity.ok(adminService.recalculateStoryPoints());
    }

    @Operation(summary = "Archive tasks completed older than N days")
    @PostMapping("/archive-old")
    public ResponseEntity<Map<String, Object>> archiveOldTasks(
            @RequestParam(defaultValue = "90") int daysOld) {
        return ResponseEntity.ok(maintenanceService.archiveOldTasks(daysOld));
    }

    @Operation(summary = "Run administrative health check")
    @PostMapping("/health-check")
    public ResponseEntity<Map<String, Object>> runHealthCheck() {
        return ResponseEntity.ok(maintenanceService.systemHealthCheck());
    }
}
