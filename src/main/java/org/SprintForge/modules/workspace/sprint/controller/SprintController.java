package org.SprintForge.modules.workspace.sprint.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintCreateRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintDuplicateRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintUpdateRequest;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintDetailResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintResponse;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;
import org.SprintForge.modules.workspace.sprint.service.SprintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Sprint Controller", description = "REST endpoints for managing sprints lifecycle and queries")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class SprintController {

    private final SprintService sprintService;

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

    @Operation(summary = "Get sprint details by ID (including project details and goals)")
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
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(sprintService.startSprint(id, actorId));
    }

    @Operation(summary = "Complete a sprint (transition ACTIVE -> COMPLETED)")
    @PostMapping("/sprints/{id}/complete")
    public ResponseEntity<SprintResponse> completeSprint(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(sprintService.completeSprint(id, actorId));
    }

    @Operation(summary = "Cancel a sprint (transition PLANNED/ACTIVE -> CANCELLED)")
    @PostMapping("/sprints/{id}/cancel")
    public ResponseEntity<SprintResponse> cancelSprint(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(sprintService.cancelSprint(id, actorId));
    }

    @Operation(summary = "Archive a sprint (transition COMPLETED/CANCELLED -> ARCHIVED)")
    @PostMapping("/sprints/{id}/archive")
    public ResponseEntity<SprintResponse> archiveSprint(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(sprintService.archiveSprint(id, actorId));
    }

    @Operation(summary = "Restore a archived sprint back to planned state")
    @PostMapping("/sprints/{id}/restore")
    public ResponseEntity<SprintResponse> restoreSprint(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(sprintService.restoreSprint(id, actorId));
    }

    @Operation(summary = "Delete a PLANNED or CANCELLED sprint")
    @DeleteMapping("/sprints/{id}")
    public ResponseEntity<Void> deleteSprint(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        sprintService.deleteSprint(id, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Duplicate a sprint")
    @PostMapping("/sprints/{id}/duplicate")
    public ResponseEntity<SprintResponse> duplicateSprint(
            @PathVariable Long id,
            @Valid @RequestBody SprintDuplicateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        SprintResponse response = sprintService.duplicateSprint(id, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}