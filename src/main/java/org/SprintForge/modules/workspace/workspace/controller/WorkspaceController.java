package org.SprintForge.modules.workspace.workspace.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.dto.request.*;
import org.SprintForge.modules.workspace.workspace.dto.response.*;
import org.SprintForge.modules.workspace.workspace.service.WorkspaceService;
import org.SprintForge.modules.workspace.workspace.service.settings.WorkspacePreferenceService;
import org.SprintForge.modules.workspace.workspace.service.settings.WorkspaceSettingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
@Tag(name = "Workspace Controller", description = "REST endpoints for managing workspace lifecycle, settings, and preferences")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final WorkspaceSettingsService workspaceSettingsService;
    private final WorkspacePreferenceService workspacePreferenceService;

    @Operation(summary = "Create a new workspace")
    @PostMapping
    public ResponseEntity<WorkspaceResponse> createWorkspace(@Valid @RequestBody WorkspaceCreateRequest request) {
        WorkspaceResponse response = workspaceService.createWorkspace(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update an existing workspace")
    @PatchMapping("/{id}")
    public ResponseEntity<WorkspaceResponse> updateWorkspace(
            @PathVariable Long id,
            @Valid @RequestBody WorkspaceUpdateRequest request) {
        WorkspaceResponse response = workspaceService.updateWorkspace(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a workspace")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long id) {
        workspaceService.deleteWorkspace(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Archive a workspace")
    @PostMapping("/{id}/archive")
    public ResponseEntity<WorkspaceResponse> archiveWorkspace(@PathVariable Long id) {
        WorkspaceResponse response = workspaceService.archiveWorkspace(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Restore an archived workspace")
    @PostMapping("/{id}/restore")
    public ResponseEntity<WorkspaceResponse> restoreWorkspace(@PathVariable Long id) {
        WorkspaceResponse response = workspaceService.restoreWorkspace(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Duplicate a workspace")
    @PostMapping("/{id}/duplicate")
    public ResponseEntity<WorkspaceResponse> duplicateWorkspace(@PathVariable Long id) {
        WorkspaceResponse response = workspaceService.duplicateWorkspace(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Clone a workspace")
    @PostMapping("/{id}/clone")
    public ResponseEntity<WorkspaceResponse> cloneWorkspace(
            @PathVariable Long id,
            @Valid @RequestBody WorkspaceCloneRequest request) {
        WorkspaceResponse response = workspaceService.cloneWorkspace(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Transfer ownership of a workspace")
    @PostMapping("/{id}/transfer-ownership")
    public ResponseEntity<WorkspaceResponse> transferOwnership(
            @PathVariable Long id,
            @RequestParam Long newOwnerId) {
        WorkspaceResponse response = workspaceService.transferOwnership(id, newOwnerId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Leave a workspace")
    @PostMapping("/{id}/leave")
    public ResponseEntity<Void> leaveWorkspace(
            @PathVariable Long id,
            @RequestParam Long userId) {
        workspaceService.leaveWorkspace(id, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get workspace by ID")
    @GetMapping("/{id}")
    public ResponseEntity<WorkspaceResponse> getWorkspace(@PathVariable Long id) {
        WorkspaceResponse response = workspaceService.getWorkspace(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get workspace by slug")
    @GetMapping("/slug/{slug}")
    public ResponseEntity<WorkspaceResponse> getWorkspaceBySlug(@PathVariable String slug) {
        WorkspaceResponse response = workspaceService.getWorkspaceBySlug(slug);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get detailed workspace information")
    @GetMapping("/{id}/details")
    public ResponseEntity<WorkspaceDetailResponse> getWorkspaceDetails(@PathVariable Long id) {
        WorkspaceDetailResponse response = workspaceService.getWorkspaceDetails(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get workspace summary information")
    @GetMapping("/{id}/summary")
    public ResponseEntity<WorkspaceSummaryResponse> getWorkspaceSummary(@PathVariable Long id) {
        WorkspaceSummaryResponse response = workspaceService.getWorkspaceSummary(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "List workspaces for a user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkspaceResponse>> listUserWorkspaces(@PathVariable Long userId) {
        List<WorkspaceResponse> response = workspaceService.listUserWorkspaces(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "List archived workspaces")
    @GetMapping("/archived")
    public ResponseEntity<List<WorkspaceResponse>> listArchivedWorkspaces() {
        List<WorkspaceResponse> response = workspaceService.listArchivedWorkspaces();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Search workspaces")
    @PostMapping("/search")
    public ResponseEntity<List<WorkspaceResponse>> searchWorkspaces(@Valid @RequestBody WorkspaceSearchRequest request) {
        List<WorkspaceResponse> response = workspaceService.searchWorkspaces(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Favorite a workspace")
    @PostMapping("/{id}/favorite")
    public ResponseEntity<Void> favoriteWorkspace(
            @PathVariable Long id,
            @RequestParam Long userId) {
        workspaceService.favoriteWorkspace(id, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Unfavorite a workspace")
    @DeleteMapping("/{id}/favorite")
    public ResponseEntity<Void> unfavoriteWorkspace(
            @PathVariable Long id,
            @RequestParam Long userId) {
        workspaceService.unfavoriteWorkspace(id, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Pin a workspace")
    @PostMapping("/{id}/pin")
    public ResponseEntity<Void> pinWorkspace(
            @PathVariable Long id,
            @RequestParam Long userId) {
        workspaceService.pinWorkspace(id, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Unpin a workspace")
    @DeleteMapping("/{id}/pin")
    public ResponseEntity<Void> unpinWorkspace(
            @PathVariable Long id,
            @RequestParam Long userId) {
        workspaceService.unpinWorkspace(id, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get recent workspaces for a user")
    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<WorkspaceResponse>> recentWorkspaces(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "5") int limit) {
        List<WorkspaceResponse> response = workspaceService.recentWorkspaces(userId, limit);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get workspace health report")
    @GetMapping("/{id}/health")
    public ResponseEntity<WorkspaceHealthReportResponse> getWorkspaceHealth(@PathVariable Long id) {
        WorkspaceHealthReportResponse response = workspaceService.getWorkspaceHealth(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get workspace settings")
    @GetMapping("/{id}/settings")
    public ResponseEntity<WorkspaceSettingsResponse> getSettings(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        WorkspaceSettingsResponse response = workspaceSettingsService.getSettings(id, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update workspace settings")
    @PutMapping("/{id}/settings")
    public ResponseEntity<WorkspaceSettingsResponse> updateSettings(
            @PathVariable Long id,
            @Valid @RequestBody WorkspaceSettingsUpdateRequest request,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        WorkspaceSettingsResponse response = workspaceSettingsService.updateSettings(id, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get workspace preferences for a user")
    @GetMapping("/{id}/users/{userId}/preferences")
    public ResponseEntity<WorkspacePreferenceResponse> getPreferences(
            @PathVariable Long id,
            @PathVariable Long userId,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        WorkspacePreferenceResponse response = workspacePreferenceService.getPreferences(id, userId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update workspace preferences for a user")
    @PutMapping("/{id}/users/{userId}/preferences")
    public ResponseEntity<WorkspacePreferenceResponse> updatePreferences(
            @PathVariable Long id,
            @PathVariable Long userId,
            @Valid @RequestBody WorkspacePreferenceRequest request,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        WorkspacePreferenceResponse response = workspacePreferenceService.updatePreferences(id, userId, request, actorId);
        return ResponseEntity.ok(response);
    }
}