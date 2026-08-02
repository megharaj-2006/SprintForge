package org.SprintForge.modules.workspace.project.insights.metrics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.insights.metrics.dto.MetricHistoryResponse;
import org.SprintForge.modules.workspace.project.insights.metrics.dto.ProjectMetricsResponse;
import org.SprintForge.modules.workspace.project.insights.metrics.service.ProjectMetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("insightsMetricsController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Metrics Controller", description = "REST endpoints for single source of truth project metrics and historical trends")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class MetricsController {

    private final ProjectMetricsService projectMetricsService;

    @Operation(summary = "Get aggregated single source of truth metrics for a project")
    @GetMapping("/projects/{projectId}/metrics")
    public ResponseEntity<ProjectMetricsResponse> getProjectMetrics(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(projectMetricsService.getProjectMetrics(projectId));
    }

    @Operation(summary = "Get historical metrics snapshots for a project")
    @GetMapping("/projects/{projectId}/metrics/history")
    public ResponseEntity<List<MetricHistoryResponse>> getMetricsHistory(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(projectMetricsService.getMetricsHistory(projectId));
    }

    @Operation(summary = "Force recalculate metrics for a project")
    @PostMapping("/metrics/recalculate")
    public ResponseEntity<ProjectMetricsResponse> recalculateMetrics(@RequestParam("projectId") Long projectId) {
        return ResponseEntity.ok(projectMetricsService.recalculateMetrics(projectId));
    }
}
