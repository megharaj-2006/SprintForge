package org.SprintForge.modules.workspace.workspace.security.session.service;

import org.SprintForge.modules.workspace.workspace.security.session.dto.response.TrustedDeviceResponse;
import org.SprintForge.modules.workspace.workspace.security.session.dto.response.WorkspaceSessionResponse;

import java.util.List;

public interface WorkspaceSecurityService {
    List<WorkspaceSessionResponse> getActiveSessions(Long workspaceId, Long requesterId);
    void revokeSession(Long workspaceId, Long sessionId, Long actorId);
    void revokeAllSessions(Long workspaceId, Long userId, Long actorId);
    List<WorkspaceSessionResponse> getLoginHistory(Long workspaceId, Long userId, int page, int size);
    List<TrustedDeviceResponse> getTrustedDevices(Long workspaceId, Long userId);
    void revokeTrustedDevice(Long workspaceId, Long deviceId, Long actorId);
}
