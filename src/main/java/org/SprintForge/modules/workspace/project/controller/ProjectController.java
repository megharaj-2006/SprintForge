package org.SprintForge.modules.workspace.project.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.dto.request.*;
import org.SprintForge.modules.workspace.project.dto.response.*;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectVisibility;
import org.SprintForge.modules.workspace.project.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Project Controller", description = "REST endpoints for managing project lifecycle, queries, settings, and membership")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Create a project in a workspace")
    @PostMapping("/workspaces/{workspaceId}/projects")
    public ResponseEntity<ProjectResponse> createProject(
            @PathVariable("workspaceId") Long workspaceId,
            @Valid @RequestBody ProjectCreateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.createProject(workspaceId, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all active projects in a workspace")
    @GetMapping("/workspaces/{workspaceId}/projects")
    public ResponseEntity<List<ProjectResponse>> getProjects(
            @PathVariable("workspaceId") Long workspaceId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<ProjectResponse> response = projectService.getProjects(workspaceId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all archived projects in a workspace")
    @GetMapping("/workspaces/{workspaceId}/projects/archived")
    public ResponseEntity<List<ProjectResponse>> getArchivedProjects(
            @PathVariable("workspaceId") Long workspaceId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<ProjectResponse> response = projectService.getArchivedProjects(workspaceId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a project by ID")
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.getProject(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a project")
    @PatchMapping("/projects/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody ProjectUpdateRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.updateProject(projectId, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Archive a project")
    @PostMapping("/projects/{projectId}/archive")
    public ResponseEntity<ProjectResponse> archiveProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.archiveProject(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Restore an archived project")
    @PostMapping("/projects/{projectId}/restore")
    public ResponseEntity<ProjectResponse> restoreProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.restoreProject(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a project (soft delete)")
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        projectService.deleteProject(projectId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Duplicate a project")
    @PostMapping("/projects/{projectId}/duplicate")
    public ResponseEntity<ProjectResponse> duplicateProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.duplicateProject(projectId, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Transfer ownership of a project")
    @PostMapping("/projects/{projectId}/transfer-ownership")
    public ResponseEntity<ProjectResponse> transferOwnership(
            @PathVariable("projectId") Long projectId,
            @RequestParam("newOwnerId") Long newOwnerId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.transferOwnership(projectId, newOwnerId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get project settings")
    @GetMapping("/projects/{projectId}/settings")
    public ResponseEntity<ProjectSettingsResponse> getSettings(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectSettingsResponse response = projectService.getSettings(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update project settings")
    @PatchMapping("/projects/{projectId}/settings")
    public ResponseEntity<ProjectSettingsResponse> updateSettings(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody ProjectSettingsRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectSettingsResponse response = projectService.updateSettings(projectId, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Change project visibility")
    @PatchMapping("/projects/{projectId}/visibility")
    public ResponseEntity<ProjectResponse> changeVisibility(
            @PathVariable("projectId") Long projectId,
            @RequestParam("visibility") ProjectVisibility visibility,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.changeVisibility(projectId, visibility, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Change project color")
    @PatchMapping("/projects/{projectId}/color")
    public ResponseEntity<ProjectResponse> changeColor(
            @PathVariable("projectId") Long projectId,
            @RequestParam("color") String color,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.changeColor(projectId, color, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Change project icon")
    @PatchMapping("/projects/{projectId}/icon")
    public ResponseEntity<ProjectResponse> changeIcon(
            @PathVariable("projectId") Long projectId,
            @RequestParam("icon") String icon,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.changeIcon(projectId, icon, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update project key")
    @PatchMapping("/projects/{projectId}/key")
    public ResponseEntity<ProjectResponse> updateProjectKey(
            @PathVariable("projectId") Long projectId,
            @RequestParam("key") String key,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectResponse response = projectService.updateProjectKey(projectId, key, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a member to a project")
    @PostMapping("/projects/{projectId}/members")
    public ResponseEntity<ProjectMemberResponse> addMember(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody AddProjectMemberRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectMemberResponse response = projectService.addMember(projectId, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all members of a project")
    @GetMapping("/projects/{projectId}/members")
    public ResponseEntity<List<ProjectMemberResponse>> getMembers(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<ProjectMemberResponse> response = projectService.getMembers(projectId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove a member from a project")
    @DeleteMapping("/projects/{projectId}/members/{memberId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable("projectId") Long projectId,
            @PathVariable("memberId") Long memberId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        projectService.removeMember(projectId, memberId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Change role of a project member")
    @PatchMapping("/projects/{projectId}/members/{memberId}/role")
    public ResponseEntity<ProjectMemberResponse> changeRole(
            @PathVariable("projectId") Long projectId,
            @PathVariable("memberId") Long memberId,
            @Valid @RequestBody UpdateProjectMemberRoleRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        ProjectMemberResponse response = projectService.changeRole(projectId, memberId, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Leave a project")
    @PostMapping("/projects/{projectId}/leave")
    public ResponseEntity<Void> leaveProject(
            @PathVariable("projectId") Long projectId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        projectService.leaveProject(projectId, actorId);
        return ResponseEntity.noContent().build();
    }
}