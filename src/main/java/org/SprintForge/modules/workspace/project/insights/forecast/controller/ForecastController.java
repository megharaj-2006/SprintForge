package org.SprintForge.modules.workspace.project.insights.forecast.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.insights.forecast.dto.CompletionForecastResponse;
import org.SprintForge.modules.workspace.project.insights.forecast.dto.ReleaseForecastResponse;
import org.SprintForge.modules.workspace.project.insights.forecast.dto.ResourceForecastResponse;
import org.SprintForge.modules.workspace.project.insights.forecast.service.ForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("insightsForecastController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Forecast Controller", description = "REST endpoints for predictive completion, release delay risk, and capacity forecasting")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ForecastController {

    private final ForecastService forecastService;

    @Operation(summary = "Get project completion forecast and estimated completion date")
    @GetMapping("/projects/{projectId}/forecast")
    public ResponseEntity<CompletionForecastResponse> getCompletionForecast(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(forecastService.getCompletionForecast(projectId));
    }

    @Operation(summary = "Get release delay probability and risk level forecast")
    @GetMapping("/projects/{projectId}/forecast/release")
    public ResponseEntity<ReleaseForecastResponse> getReleaseForecast(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(forecastService.getReleaseForecast(projectId));
    }

    @Operation(summary = "Get 30-day resource capacity and deficit forecast")
    @GetMapping("/projects/{projectId}/forecast/resources")
    public ResponseEntity<ResourceForecastResponse> getResourceForecast(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(forecastService.getResourceForecast(projectId));
    }
}
