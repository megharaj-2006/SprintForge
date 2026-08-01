package org.SprintForge.modules.workspace.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.dto.response.WorkloadReportResponse;
import org.SprintForge.modules.workspace.project.service.WorkloadService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Validated
@Tag(name = "Workload Controller", description = "REST endpoints for user capacity, allocation, and workload reports")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class WorkloadController {

    private final WorkloadService workloadService;

    @Operation(summary = "Get team workload report for a project")
    @GetMapping("/{id}/workload")
    public ResponseEntity<WorkloadReportResponse> getProjectWorkload(@PathVariable Long id) {
        WorkloadReportResponse response = workloadService.getProjectWorkload(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get most busy users sorted by assigned hours")
    @GetMapping("/{id}/workload/busy")
    public ResponseEntity<List<WorkloadReportResponse.UserWorkloadSummary>> getBusyUsers(@PathVariable Long id) {
        List<WorkloadReportResponse.UserWorkloadSummary> response = workloadService.getBusyUsers(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get overloaded users exceeding weekly capacity")
    @GetMapping("/{id}/workload/overloaded")
    public ResponseEntity<List<WorkloadReportResponse.UserWorkloadSummary>> getOverloadedUsers(@PathVariable Long id) {
        List<WorkloadReportResponse.UserWorkloadSummary> response = workloadService.getOverloadedUsers(id);
        return ResponseEntity.ok(response);
    }
}
