package org.SprintForge.modules.workspace.project.governance.dashboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.dashboard.dto.GovernanceDashboardResponse;
import org.SprintForge.modules.workspace.project.governance.dashboard.dto.GovernanceSummaryResponse;
import org.SprintForge.modules.workspace.project.governance.dashboard.service.GovernanceDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("governanceDashboardController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Governance Dashboard Controller", description = "REST endpoints for enterprise project governance score, compliance metrics, and risk overview")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class GovernanceDashboardController {

    private final GovernanceDashboardService governanceDashboardService;

    @Operation(summary = "Get full governance dashboard metrics for a project")
    @GetMapping("/projects/{projectId}/governance")
    public ResponseEntity<GovernanceDashboardResponse> getProjectGovernance(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(governanceDashboardService.getProjectGovernance(projectId));
    }

    @Operation(summary = "Get executive governance summary for a project")
    @GetMapping("/projects/{projectId}/governance/summary")
    public ResponseEntity<GovernanceSummaryResponse> getProjectGovernanceSummary(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(governanceDashboardService.getProjectGovernanceSummary(projectId));
    }
}
