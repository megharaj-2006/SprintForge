package org.SprintForge.modules.workspace.project.governance.change.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.change.dto.request.CreateChangeRequest;
import org.SprintForge.modules.workspace.project.governance.change.dto.request.UpdateChangeRequest;
import org.SprintForge.modules.workspace.project.governance.change.dto.response.ChangeResponse;
import org.SprintForge.modules.workspace.project.governance.change.service.ProjectChangeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("governanceChangeController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Change Controller", description = "REST endpoints for managing project change requests and impact evaluations")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ChangeController {

    private final ProjectChangeService changeService;

    @Operation(summary = "Create a project change request")
    @PostMapping("/projects/{projectId}/changes")
    public ResponseEntity<ChangeResponse> createChangeRequest(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody CreateChangeRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(changeService.createChangeRequest(projectId, request, actorId));
    }

    @Operation(summary = "Get all change requests for a project")
    @GetMapping("/projects/{projectId}/changes")
    public ResponseEntity<List<ChangeResponse>> getProjectChanges(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(changeService.getProjectChanges(projectId));
    }

    @Operation(summary = "Get change request details by ID")
    @GetMapping("/changes/{changeId}")
    public ResponseEntity<ChangeResponse> getChange(@PathVariable("changeId") Long changeId) {
        return ResponseEntity.ok(changeService.getChange(changeId));
    }

    @Operation(summary = "Update change request status or details")
    @PatchMapping("/changes/{changeId}")
    public ResponseEntity<ChangeResponse> updateChangeRequest(
            @PathVariable("changeId") Long changeId,
            @Valid @RequestBody UpdateChangeRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(changeService.updateChangeRequest(changeId, request, actorId));
    }

    @Operation(summary = "Delete / archive a change request")
    @DeleteMapping("/changes/{changeId}")
    public ResponseEntity<Void> deleteChange(
            @PathVariable("changeId") Long changeId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        changeService.deleteChange(changeId, actorId);
        return ResponseEntity.noContent().build();
    }
}
