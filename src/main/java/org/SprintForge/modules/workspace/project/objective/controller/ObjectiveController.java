package org.SprintForge.modules.workspace.project.objective.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.objective.dto.request.CreateObjectiveRequest;
import org.SprintForge.modules.workspace.project.objective.dto.request.UpdateObjectiveRequest;
import org.SprintForge.modules.workspace.project.objective.dto.response.ObjectiveResponse;
import org.SprintForge.modules.workspace.project.objective.service.ObjectiveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("strategicObjectiveController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Objective Controller", description = "REST endpoints for managing goal objectives")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ObjectiveController {

    private final ObjectiveService objectiveService;

    @Operation(summary = "Create an objective under a goal")
    @PostMapping("/goals/{goalId}/objectives")
    public ResponseEntity<ObjectiveResponse> createObjective(
            @PathVariable("goalId") Long goalId,
            @Valid @RequestBody CreateObjectiveRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(objectiveService.createObjective(goalId, request, actorId));
    }

    @Operation(summary = "Get all objectives for a goal")
    @GetMapping("/goals/{goalId}/objectives")
    public ResponseEntity<List<ObjectiveResponse>> getObjectives(@PathVariable("goalId") Long goalId) {
        return ResponseEntity.ok(objectiveService.getObjectives(goalId));
    }

    @Operation(summary = "Get objective details by ID")
    @GetMapping("/objectives/{objectiveId}")
    public ResponseEntity<ObjectiveResponse> getObjective(@PathVariable("objectiveId") Long objectiveId) {
        return ResponseEntity.ok(objectiveService.getObjective(objectiveId));
    }

    @Operation(summary = "Update objective details")
    @PatchMapping("/objectives/{objectiveId}")
    public ResponseEntity<ObjectiveResponse> updateObjective(
            @PathVariable("objectiveId") Long objectiveId,
            @Valid @RequestBody UpdateObjectiveRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(objectiveService.updateObjective(objectiveId, request, actorId));
    }

    @Operation(summary = "Delete an objective")
    @DeleteMapping("/objectives/{objectiveId}")
    public ResponseEntity<Void> deleteObjective(
            @PathVariable("objectiveId") Long objectiveId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        objectiveService.deleteObjective(objectiveId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Move an objective to a different goal")
    @PostMapping("/objectives/{objectiveId}/move")
    public ResponseEntity<ObjectiveResponse> moveObjective(
            @PathVariable("objectiveId") Long objectiveId,
            @RequestParam("newGoalId") Long newGoalId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(objectiveService.moveObjective(objectiveId, newGoalId, actorId));
    }
}
