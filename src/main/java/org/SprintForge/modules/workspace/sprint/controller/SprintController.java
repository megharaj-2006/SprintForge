package org.SprintForge.modules.workspace.sprint.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.sprint.dto.request.*;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintBurndownResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintDetailResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintResponse;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;
import org.SprintForge.modules.workspace.sprint.service.SprintPlanningApplicationService;
import org.SprintForge.modules.workspace.sprint.service.SprintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Sprint Controller", description = "REST endpoints for managing sprints lifecycle and planning workflows")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class SprintController {

    private final SprintService sprintService;
    private final SprintPlanningApplicationService sprintPlanningApplicationService;

    @Operation(summary = "Create a new sprint for a project")
    @PostMapping("/projects/{projectId}/sprints")
    public ResponseEntity<SprintResponse> createSprint(
            @PathVariable Long projectId,
            @Valid @RequestBody SprintCreateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        request.setProjectId(projectId);
        SprintResponse response = sprintService.createSprint(request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get sprints for a project (optionally filtered by status)")
    @GetMapping("/projects/{projectId}/sprints")
    public ResponseEntity<List<SprintResponse>> getProjectSprints(
            @PathVariable Long projectId,
            @RequestParam(value = "status", required = false) SprintStatus status,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        if (status != null) {
            return ResponseEntity.ok(sprintService.getSprintsByStatus(projectId, status, actorId));
        }
        return ResponseEntity.ok(sprintService.getProjectSprints(projectId, actorId));
    }

    @Operation(summary = "Get the active sprint for a project")
    @GetMapping("/projects/{projectId}/sprints/active")
    public ResponseEntity<SprintResponse> getActiveSprint(
            @PathVariable Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(sprintService.getActiveSprint(projectId, actorId));
    }

    @Operation(summary = "Get sprint by ID")
    @GetMapping("/sprints/{id}")
    public ResponseEntity<SprintResponse> getSprint(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(sprintService.getSprint(id, actorId));
    }

    @Operation(summary = "Get sprint details by ID")
    @GetMapping("/sprints/{id}/detail")
    public ResponseEntity<SprintDetailResponse> getSprintDetail(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(sprintService.getSprintDetail(id, actorId));
    }

    @Operation(summary = "Update sprint details")
    @PatchMapping("/sprints/{id}")
    public ResponseEntity<SprintResponse> updateSprint(
            @PathVariable Long id,
            @Valid @RequestBody SprintUpdateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(sprintService.updateSprint(id, request, actorId));
    }

    @Operation(summary = "Start a sprint (transition PLANNED -> ACTIVE)")
    @PostMapping("/sprints/{id}/start")
    public ResponseEntity<SprintResponse> startSprint(
            @PathVariable Long id,
            @RequestBody(required = false) SprintStartRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(sprintPlanningApplicationService.startSprint(id, request, actorId));
    }

    @Operation(summary = "Complete a sprint (transition ACTIVE -> COMPLETED)")
    @PostMapping("/sprints/{id}/complete")
    public ResponseEntity<SprintResponse> completeSprint(
            @PathVariable Long id,
            @RequestBody(required = false) SprintCompleteRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(sprintPlanningApplicationService.completeSprint(id, request, actorId));
    }

    @Operation(summary = "Archive a sprint")
    @PostMapping("/sprints/{id}/archive")
    public ResponseEntity<SprintResponse> archiveSprint(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(sprintPlanningApplicationService.archiveSprint(id, actorId));
    }

    @Operation(summary = "Clone a sprint")
    @PostMapping("/sprints/{id}/clone")
    public ResponseEntity<SprintResponse> cloneSprint(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        SprintResponse response = sprintPlanningApplicationService.cloneSprint(id, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Bulk move tasks into a sprint")
    @PostMapping("/sprints/{id}/tasks")
    public ResponseEntity<SprintResponse> moveTasksToSprint(
            @PathVariable Long id,
            @RequestBody List<Long> taskIds,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        SprintResponse response = sprintPlanningApplicationService.moveTasksToSprint(id, taskIds, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get sprint burndown and burnup chart data")
    @GetMapping("/sprints/{id}/burndown")
    public ResponseEntity<SprintBurndownResponse> getSprintBurndown(@PathVariable Long id) {
        SprintBurndownResponse response = sprintPlanningApplicationService.getBurndown(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a PLANNED or CANCELLED sprint")
    @DeleteMapping("/sprints/{id}")
    public ResponseEntity<Void> deleteSprint(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        sprintService.deleteSprint(id, actorId);
        return ResponseEntity.noContent().build();
    }
}