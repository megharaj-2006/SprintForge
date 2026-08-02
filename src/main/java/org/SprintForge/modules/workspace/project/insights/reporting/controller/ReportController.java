package org.SprintForge.modules.workspace.project.insights.reporting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.insights.reporting.dto.request.CreateReportRequest;
import org.SprintForge.modules.workspace.project.insights.reporting.dto.response.ReportExecutionResponse;
import org.SprintForge.modules.workspace.project.insights.reporting.dto.response.SavedReportResponse;
import org.SprintForge.modules.workspace.project.insights.reporting.service.ProjectReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("insightsReportController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Report Controller", description = "REST endpoints for creating, retrieving, and generating PDF/CSV project reports")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ReportController {

    private final ProjectReportService projectReportService;

    @Operation(summary = "Create a saved report template")
    @PostMapping("/reports")
    public ResponseEntity<SavedReportResponse> createReport(
            @Valid @RequestBody CreateReportRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectReportService.createReport(request, actorId));
    }

    @Operation(summary = "Get all saved report templates for a project")
    @GetMapping("/projects/{projectId}/reports")
    public ResponseEntity<List<SavedReportResponse>> getProjectReports(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(projectReportService.getProjectReports(projectId));
    }

    @Operation(summary = "Generate/export a report instantly")
    @PostMapping("/reports/{reportId}/generate")
    public ResponseEntity<ReportExecutionResponse> generateReport(
            @PathVariable("reportId") Long reportId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(projectReportService.generateReport(reportId, actorId));
    }
}
