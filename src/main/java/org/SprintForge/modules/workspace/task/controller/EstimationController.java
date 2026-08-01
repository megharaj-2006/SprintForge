package org.SprintForge.modules.workspace.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.request.EstimateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.EstimationAccuracyReportResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskEstimateResponse;
import org.SprintForge.modules.workspace.task.service.EstimationService;
import org.SprintForge.modules.workspace.task.service.VarianceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Estimation Controller", description = "REST endpoints for task story points and estimation analytics")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class EstimationController {

    private final EstimationService estimationService;
    private final VarianceService varianceService;

    @Operation(summary = "Submit task estimate (story points or hours)")
    @PostMapping("/tasks/{id}/estimate")
    public ResponseEntity<TaskEstimateResponse> estimateTask(
            @PathVariable Long id,
            @Valid @RequestBody EstimateTaskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        TaskEstimateResponse response = estimationService.estimateTask(id, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get task estimate history")
    @GetMapping("/tasks/{id}/estimate/history")
    public ResponseEntity<List<TaskEstimateResponse>> getEstimateHistory(@PathVariable Long id) {
        List<TaskEstimateResponse> response = estimationService.getEstimateHistory(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get project estimation accuracy report")
    @GetMapping("/projects/{id}/estimation/accuracy")
    public ResponseEntity<EstimationAccuracyReportResponse> getEstimationAccuracy(@PathVariable Long id) {
        EstimationAccuracyReportResponse response = varianceService.calculateProjectAccuracy(id);
        return ResponseEntity.ok(response);
    }
}
