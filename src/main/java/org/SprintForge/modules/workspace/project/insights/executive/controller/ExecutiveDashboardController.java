package org.SprintForge.modules.workspace.project.insights.executive.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.insights.executive.dto.ExecutiveDashboardResponse;
import org.SprintForge.modules.workspace.project.insights.executive.service.ExecutiveDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("insightsExecutiveDashboardController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Executive Dashboard Controller", description = "REST endpoints for C-level management visibility, portfolio completion rollups, and cross-project KPIs")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ExecutiveDashboardController {

    private final ExecutiveDashboardService executiveDashboardService;

    @Operation(summary = "Get executive dashboard metrics across all portfolios and projects")
    @GetMapping("/executive/dashboard")
    public ResponseEntity<ExecutiveDashboardResponse> getExecutiveDashboard(@RequestParam("workspaceId") Long workspaceId) {
        return ResponseEntity.ok(executiveDashboardService.getExecutiveDashboard(workspaceId));
    }
}
