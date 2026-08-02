package org.SprintForge.modules.workspace.project.governance.decision.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.decision.dto.request.CreateDecisionRequest;
import org.SprintForge.modules.workspace.project.governance.decision.dto.request.UpdateDecisionRequest;
import org.SprintForge.modules.workspace.project.governance.decision.dto.response.DecisionResponse;
import org.SprintForge.modules.workspace.project.governance.decision.service.DecisionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("governanceDecisionController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Decision Controller", description = "REST endpoints for managing architectural and business project decisions")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class DecisionController {

    private final DecisionService decisionService;

    @Operation(summary = "Create a project decision record")
    @PostMapping("/projects/{projectId}/decisions")
    public ResponseEntity<DecisionResponse> createDecision(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody CreateDecisionRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(decisionService.createDecision(projectId, request, actorId));
    }

    @Operation(summary = "Get all decisions for a project")
    @GetMapping("/projects/{projectId}/decisions")
    public ResponseEntity<List<DecisionResponse>> getDecisions(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(decisionService.getDecisions(projectId));
    }

    @Operation(summary = "Get decision details by ID")
    @GetMapping("/decisions/{decisionId}")
    public ResponseEntity<DecisionResponse> getDecision(@PathVariable("decisionId") Long decisionId) {
        return ResponseEntity.ok(decisionService.getDecision(decisionId));
    }

    @Operation(summary = "Update decision record")
    @PatchMapping("/decisions/{decisionId}")
    public ResponseEntity<DecisionResponse> updateDecision(
            @PathVariable("decisionId") Long decisionId,
            @Valid @RequestBody UpdateDecisionRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(decisionService.updateDecision(decisionId, request, actorId));
    }

    @Operation(summary = "Delete / archive decision record")
    @DeleteMapping("/decisions/{decisionId}")
    public ResponseEntity<Void> deleteDecision(
            @PathVariable("decisionId") Long decisionId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        decisionService.deleteDecision(decisionId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Approve a decision")
    @PostMapping("/decisions/{decisionId}/approve")
    public ResponseEntity<DecisionResponse> approveDecision(
            @PathVariable("decisionId") Long decisionId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(decisionService.approveDecision(decisionId, actorId));
    }

    @Operation(summary = "Supersede a decision with a newer record")
    @PostMapping("/decisions/{decisionId}/supersede")
    public ResponseEntity<DecisionResponse> supersedeDecision(
            @PathVariable("decisionId") Long decisionId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(decisionService.supersedeDecision(decisionId, actorId));
    }
}
