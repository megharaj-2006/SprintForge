package org.SprintForge.modules.workspace.workspace.security.session.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.security.session.dto.response.TrustedDeviceResponse;
import org.SprintForge.modules.workspace.workspace.security.session.dto.response.WorkspaceSessionResponse;
import org.SprintForge.modules.workspace.workspace.security.session.service.WorkspaceSecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workspaces/{workspaceId}")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Tag(name = "Session Management")
public class WorkspaceSessionController {

    private final WorkspaceSecurityService workspaceSecurityService;

    @GetMapping("/sessions")
    public List<WorkspaceSessionResponse> getActiveSessions(
            @PathVariable Long workspaceId,
            @RequestHeader("X-User-Id") Long actorId) {
        return workspaceSecurityService.getActiveSessions(workspaceId, actorId);
    }

    @DeleteMapping("/sessions/{sessionId}")
    public ResponseEntity<Void> revokeSession(
            @PathVariable Long workspaceId,
            @PathVariable Long sessionId,
            @RequestHeader("X-User-Id") Long actorId) {
        workspaceSecurityService.revokeSession(workspaceId, sessionId, actorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/sessions")
    public ResponseEntity<Void> revokeAllSessions(
            @PathVariable Long workspaceId,
            @RequestParam Long userId,
            @RequestHeader("X-User-Id") Long actorId) {
        workspaceSecurityService.revokeAllSessions(workspaceId, userId, actorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/trusted-devices")
    public List<TrustedDeviceResponse> getTrustedDevices(
            @PathVariable Long workspaceId,
            @RequestParam Long userId) {
        return workspaceSecurityService.getTrustedDevices(workspaceId, userId);
    }

    @DeleteMapping("/trusted-devices/{deviceId}")
    public ResponseEntity<Void> revokeTrustedDevice(
            @PathVariable Long workspaceId,
            @PathVariable Long deviceId,
            @RequestHeader("X-User-Id") Long actorId) {
        workspaceSecurityService.revokeTrustedDevice(workspaceId, deviceId, actorId);
        return ResponseEntity.noContent().build();
    }
}
