package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.response.TaskOperationalReportResponse;
import org.SprintForge.modules.workspace.task.service.TaskReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Report Controller", description = "REST endpoints for operational task, workload, and time reports")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TaskReportController {

    private final TaskReportService taskReportService;

    @Operation(summary = "Get task operational report")
    @GetMapping("/tasks")
    public ResponseEntity<TaskOperationalReportResponse> getTaskReport(@RequestParam Long projectId) {
        return ResponseEntity.ok(taskReportService.getTaskReport(projectId));
    }

    @Operation(summary = "Get overdue tasks report")
    @GetMapping("/overdue")
    public ResponseEntity<TaskOperationalReportResponse> getOverdueReport(@RequestParam Long projectId) {
        return ResponseEntity.ok(taskReportService.getTaskReport(projectId));
    }

    @Operation(summary = "Get workload report")
    @GetMapping("/workload")
    public ResponseEntity<TaskOperationalReportResponse> getWorkloadReport(@RequestParam Long projectId) {
        return ResponseEntity.ok(taskReportService.getTaskReport(projectId));
    }

    @Operation(summary = "Get time logged report")
    @GetMapping("/time")
    public ResponseEntity<TaskOperationalReportResponse> getTimeReport(@RequestParam Long projectId) {
        return ResponseEntity.ok(taskReportService.getTaskReport(projectId));
    }
}
