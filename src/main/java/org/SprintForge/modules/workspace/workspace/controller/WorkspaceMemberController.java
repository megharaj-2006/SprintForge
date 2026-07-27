package org.SprintForge.modules.workspace.workspace.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.dto.request.AddWorkspaceMemberRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceMemberResponse;
import org.SprintForge.modules.workspace.workspace.dto.response.MemberSearchResponse;
import org.SprintForge.modules.workspace.workspace.service.WorkspaceMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workspaces/{workspaceId}/members")
@RequiredArgsConstructor
@Tag(name = "Workspace Member Controller", description = "REST endpoints for managing workspace members")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class WorkspaceMemberController {

    private final WorkspaceMemberService workspaceMemberService;

    @Operation(summary = "Add a new member to a workspace")
    @PostMapping
    public ResponseEntity<WorkspaceMemberResponse> addMember(
            @PathVariable Long workspaceId,
            @Valid @RequestBody AddWorkspaceMemberRequest request,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        request.setWorkspaceId(workspaceId);
        WorkspaceMemberResponse response = workspaceMemberService.addMember(request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Remove a member from a workspace")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long workspaceId,
            @PathVariable Long userId,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        workspaceMemberService.removeMember(workspaceId, userId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Suspend a workspace member")
    @PostMapping("/{userId}/suspend")
    public ResponseEntity<WorkspaceMemberResponse> suspendMember(
            @PathVariable Long workspaceId,
            @PathVariable Long userId,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        WorkspaceMemberResponse response = workspaceMemberService.suspendMember(workspaceId, userId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Reactivate a suspended workspace member")
    @PostMapping("/{userId}/reactivate")
    public ResponseEntity<WorkspaceMemberResponse> reactivateMember(
            @PathVariable Long workspaceId,
            @PathVariable Long userId,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        WorkspaceMemberResponse response = workspaceMemberService.reactivateMember(workspaceId, userId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Change a workspace member's role")
    @PutMapping("/{userId}/role")
    public ResponseEntity<WorkspaceMemberResponse> changeMemberRole(
            @PathVariable Long workspaceId,
            @PathVariable Long userId,
            @RequestParam Long roleId,
            @RequestHeader(value = "X-User-Id") Long actorId) {
        WorkspaceMemberResponse response = workspaceMemberService.changeMemberRole(workspaceId, userId, roleId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all members of a workspace")
    @GetMapping
    public ResponseEntity<List<WorkspaceMemberResponse>> getMembers(@PathVariable Long workspaceId) {
        List<WorkspaceMemberResponse> response = workspaceMemberService.getMembers(workspaceId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get active members of a workspace")
    @GetMapping("/active")
    public ResponseEntity<List<WorkspaceMemberResponse>> getActiveMembers(@PathVariable Long workspaceId) {
        List<WorkspaceMemberResponse> response = workspaceMemberService.getActiveMembers(workspaceId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get pending invitations/members of a workspace")
    @GetMapping("/pending")
    public ResponseEntity<List<WorkspaceMemberResponse>> getPendingMembers(@PathVariable Long workspaceId) {
        List<WorkspaceMemberResponse> response = workspaceMemberService.getPendingMembers(workspaceId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get workspace administrators")
    @GetMapping("/admins")
    public ResponseEntity<List<WorkspaceMemberResponse>> getWorkspaceAdmins(@PathVariable Long workspaceId) {
        List<WorkspaceMemberResponse> response = workspaceMemberService.getWorkspaceAdmins(workspaceId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get workspace owner")
    @GetMapping("/owner")
    public ResponseEntity<WorkspaceMemberResponse> getWorkspaceOwner(@PathVariable Long workspaceId) {
        WorkspaceMemberResponse response = workspaceMemberService.getWorkspaceOwner(workspaceId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Search workspace members")
    @GetMapping("/search")
    public ResponseEntity<MemberSearchResponse> searchMembers(
            @PathVariable Long workspaceId,
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        MemberSearchResponse response = workspaceMemberService.searchMembers(workspaceId, query, page, size);
        return ResponseEntity.ok(response);
    }
}
