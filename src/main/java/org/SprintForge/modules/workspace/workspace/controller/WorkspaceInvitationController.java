package org.SprintForge.modules.workspace.workspace.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.dto.request.InviteMemberRequest;
import org.SprintForge.modules.workspace.workspace.dto.response.WorkspaceInvitationResponse;
import org.SprintForge.modules.workspace.workspace.service.invitation.WorkspaceInvitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Workspace Invitation Controller", description = "REST endpoints for managing workspace invitations lifecycle")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class WorkspaceInvitationController {

    private final WorkspaceInvitationService workspaceInvitationService;

    @Operation(summary = "Invite a new member to a workspace")
    @PostMapping("/workspaces/{id}/invitations")
    public ResponseEntity<WorkspaceInvitationResponse> inviteMember(
            @PathVariable("id") Long workspaceId,
            @Valid @RequestBody InviteMemberRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        WorkspaceInvitationResponse response = workspaceInvitationService.inviteMember(workspaceId, request, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all invitations for a workspace")
    @GetMapping("/workspaces/{id}/invitations")
    public ResponseEntity<List<WorkspaceInvitationResponse>> getWorkspaceInvitations(
            @PathVariable("id") Long workspaceId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        List<WorkspaceInvitationResponse> response = workspaceInvitationService.getWorkspaceInvitations(workspaceId, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Accept workspace invitation")
    @PostMapping("/invitations/{token}/accept")
    public ResponseEntity<WorkspaceInvitationResponse> acceptInvitation(
            @PathVariable("token") String token,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId) {
        WorkspaceInvitationResponse response = workspaceInvitationService.acceptInvitation(token, userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Reject workspace invitation")
    @PostMapping("/invitations/{token}/reject")
    public ResponseEntity<WorkspaceInvitationResponse> rejectInvitation(
            @PathVariable("token") String token,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long userId) {
        WorkspaceInvitationResponse response = workspaceInvitationService.rejectInvitation(token, userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Resend workspace invitation")
    @PostMapping("/invitations/{token}/resend")
    public ResponseEntity<WorkspaceInvitationResponse> resendInvitation(
            @PathVariable("token") String token,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        WorkspaceInvitationResponse response = workspaceInvitationService.resendInvitation(token, actorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cancel / delete workspace invitation")
    @DeleteMapping("/invitations/{token}")
    public ResponseEntity<Void> cancelInvitation(
            @PathVariable("token") String token,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        workspaceInvitationService.cancelInvitation(token, actorId);
        return ResponseEntity.noContent().build();
    }
}
