package org.SprintForge.modules.workspace.workspace.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.dto.request.AssignWorkspaceRoleRequest;
import org.SprintForge.modules.workspace.workspace.dto.request.UpdateWorkspaceRoleRequest;
import org.SprintForge.modules.workspace.workspace.dto.request.WorkspaceRoleCreateRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceMemberResponse;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceRoleResponse;
import org.SprintForge.modules.workspace.workspace.service.WorkspaceRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workspaces")
@RequiredArgsConstructor
@Tag(name = "Workspace Role Controller", description = "REST endpoints for managing workspace roles and permissions")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class WorkspaceRoleController {

    private final WorkspaceRoleService workspaceRoleService;

    @Operation(summary = "Create a custom role for a workspace")
    @PostMapping("/{workspaceId}/roles")
    public ResponseEntity<WorkspaceRoleResponse> createRole(
            @PathVariable Long workspaceId,
            @Valid @RequestBody WorkspaceRoleCreateRequest request,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        request.setWorkspaceId(workspaceId);
        WorkspaceRoleResponse response = workspaceRoleService.createRole(request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update an existing workspace role")
    @PutMapping("/roles/{roleId}")
    public ResponseEntity<WorkspaceRoleResponse> updateRole(
            @PathVariable Long roleId,
            @Valid @RequestBody UpdateWorkspaceRoleRequest request,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        WorkspaceRoleResponse response = workspaceRoleService.updateRole(roleId, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a workspace role")
    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<Void> deleteRole(
            @PathVariable Long roleId,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        workspaceRoleService.deleteRole(roleId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Duplicate a workspace role")
    @PostMapping("/roles/{roleId}/duplicate")
    public ResponseEntity<WorkspaceRoleResponse> duplicateRole(
            @PathVariable Long roleId,
            @RequestParam String newName,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        WorkspaceRoleResponse response = workspaceRoleService.duplicateRole(roleId, newName, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Reorder workspace roles")
    @PutMapping("/{workspaceId}/roles/reorder")
    public ResponseEntity<List<WorkspaceRoleResponse>> reorderRoles(
            @PathVariable Long workspaceId,
            @RequestBody List<Long> roleIds,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        List<WorkspaceRoleResponse> response = workspaceRoleService.reorderRoles(workspaceId, roleIds, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all roles of a workspace")
    @GetMapping("/{workspaceId}/roles")
    public ResponseEntity<List<WorkspaceRoleResponse>> getRoles(@PathVariable Long workspaceId) {
        List<WorkspaceRoleResponse> response = workspaceRoleService.getRoles(workspaceId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a single workspace role by ID")
    @GetMapping("/roles/{roleId}")
    public ResponseEntity<WorkspaceRoleResponse> getRole(@PathVariable Long roleId) {
        WorkspaceRoleResponse response = workspaceRoleService.getRole(roleId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Set a default role for a workspace")
    @PutMapping("/{workspaceId}/roles/{roleId}/default")
    public ResponseEntity<WorkspaceRoleResponse> setDefaultRole(
            @PathVariable Long workspaceId,
            @PathVariable Long roleId,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        WorkspaceRoleResponse response = workspaceRoleService.setDefaultRole(workspaceId, roleId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Assign a role to a workspace member")
    @PostMapping("/roles/assign")
    public ResponseEntity<WorkspaceMemberResponse> assignRole(
            @Valid @RequestBody AssignWorkspaceRoleRequest request,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        WorkspaceMemberResponse response = workspaceRoleService.assignRole(request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove a custom role from a workspace member")
    @DeleteMapping("/{workspaceId}/members/{userId}/role")
    public ResponseEntity<WorkspaceMemberResponse> removeRole(
            @PathVariable Long workspaceId,
            @PathVariable Long userId,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        WorkspaceMemberResponse response = workspaceRoleService.removeRole(workspaceId, userId, actorId);
        return ResponseEntity.ok(response);
    }
}
