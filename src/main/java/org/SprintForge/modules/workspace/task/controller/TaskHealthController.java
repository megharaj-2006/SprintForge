package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.response.TaskHealthResponse;
import org.SprintForge.modules.workspace.task.service.ComplianceService;
import org.SprintForge.modules.workspace.task.service.TaskHealthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Health Controller", description = "REST endpoints for task health scoring, stale detection, and compliance governance")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TaskHealthController {

    private final TaskHealthService healthService;
    private final ComplianceService complianceService;

    @Operation(summary = "Get task health score and warnings")
    @GetMapping("/{id}/health")
    public ResponseEntity<TaskHealthResponse> getTaskHealth(@PathVariable Long id) {
        return ResponseEntity.ok(healthService.calculateTaskHealth(id));
    }

    @Operation(summary = "Get non-compliant tasks lacking required fields")
    @GetMapping("/non-compliant")
    public ResponseEntity<List<TaskHealthResponse>> getNonCompliantTasks() {
        return ResponseEntity.ok(complianceService.getNonCompliantTasks());
    }

    @Operation(summary = "Get stale tasks un-updated for 14+ days")
    @GetMapping("/stale")
    public ResponseEntity<List<TaskHealthResponse>> getStaleTasks() {
        return ResponseEntity.ok(healthService.getStaleTasks());
    }

    @Operation(summary = "Get high-risk tasks requiring intervention")
    @GetMapping("/risk")
    public ResponseEntity<List<TaskHealthResponse>> getRiskTasks() {
        return ResponseEntity.ok(healthService.getRiskTasks());
    }
}
