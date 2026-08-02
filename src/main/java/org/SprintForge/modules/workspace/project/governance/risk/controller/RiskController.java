package org.SprintForge.modules.workspace.project.governance.risk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.risk.dto.request.CreateRiskRequest;
import org.SprintForge.modules.workspace.project.governance.risk.dto.request.UpdateRiskRequest;
import org.SprintForge.modules.workspace.project.governance.risk.dto.response.RiskResponse;
import org.SprintForge.modules.workspace.project.governance.risk.service.RiskService;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("governanceRiskController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Risk Controller", description = "REST endpoints for managing project risk register and mitigation plans")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class RiskController {

    private final RiskService riskService;

    @Operation(summary = "Create a project risk")
    @PostMapping("/projects/{projectId}/risks")
    public ResponseEntity<RiskResponse> createRisk(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody CreateRiskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(riskService.createRisk(projectId, request, actorId));
    }

    @Operation(summary = "Get all risks for a project")
    @GetMapping("/projects/{projectId}/risks")
    public ResponseEntity<List<RiskResponse>> getRisks(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(riskService.getRisks(projectId));
    }

    @Operation(summary = "Get risk details by ID")
    @GetMapping("/risks/{riskId}")
    public ResponseEntity<RiskResponse> getRisk(@PathVariable("riskId") Long riskId) {
        return ResponseEntity.ok(riskService.getRisk(riskId));
    }

    @Operation(summary = "Update risk details")
    @PatchMapping("/risks/{riskId}")
    public ResponseEntity<RiskResponse> updateRisk(
            @PathVariable("riskId") Long riskId,
            @Valid @RequestBody UpdateRiskRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(riskService.updateRisk(riskId, request, actorId));
    }

    @Operation(summary = "Delete / archive a risk")
    @DeleteMapping("/risks/{riskId}")
    public ResponseEntity<Void> deleteRisk(
            @PathVariable("riskId") Long riskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        riskService.deleteRisk(riskId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Generate a mitigation task for a risk")
    @PostMapping("/risks/{riskId}/mitigation")
    public ResponseEntity<TaskResponse> createMitigationTask(
            @PathVariable("riskId") Long riskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(riskService.createMitigationTask(riskId, actorId));
    }

    @Operation(summary = "Resolve a risk")
    @PostMapping("/risks/{riskId}/resolve")
    public ResponseEntity<RiskResponse> resolveRisk(
            @PathVariable("riskId") Long riskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(riskService.resolveRisk(riskId, actorId));
    }

    @Operation(summary = "Reopen a risk")
    @PostMapping("/risks/{riskId}/reopen")
    public ResponseEntity<RiskResponse> reopenRisk(
            @PathVariable("riskId") Long riskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(riskService.reopenRisk(riskId, actorId));
    }
}
