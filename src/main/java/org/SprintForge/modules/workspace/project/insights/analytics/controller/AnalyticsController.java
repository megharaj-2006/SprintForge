package org.SprintForge.modules.workspace.project.insights.analytics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.insights.analytics.dto.ProjectAnalyticsResponse;
import org.SprintForge.modules.workspace.project.insights.analytics.dto.QualityAnalyticsResponse;
import org.SprintForge.modules.workspace.project.insights.analytics.dto.TeamAnalyticsResponse;
import org.SprintForge.modules.workspace.project.insights.analytics.service.ProjectAnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("insightsAnalyticsController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Analytics Controller", description = "REST endpoints for productivity, team workload, and quality analytics")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class AnalyticsController {

    private final ProjectAnalyticsService projectAnalyticsService;

    @Operation(summary = "Get overall project analytics (velocity, throughput, cycle time)")
    @GetMapping("/projects/{projectId}/analytics")
    public ResponseEntity<ProjectAnalyticsResponse> getProjectAnalytics(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(projectAnalyticsService.getProjectAnalytics(projectId));
    }

    @Operation(summary = "Get team productivity and workload balance analytics")
    @GetMapping("/projects/{projectId}/analytics/team")
    public ResponseEntity<TeamAnalyticsResponse> getTeamAnalytics(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(projectAnalyticsService.getTeamAnalytics(projectId));
    }

    @Operation(summary = "Get project quality, defect, and blocked task analytics")
    @GetMapping("/projects/{projectId}/analytics/quality")
    public ResponseEntity<QualityAnalyticsResponse> getQualityAnalytics(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(projectAnalyticsService.getQualityAnalytics(projectId));
    }
}
