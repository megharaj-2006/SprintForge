package org.SprintForge.modules.workspace.workspace.security.session.service;

import org.SprintForge.modules.workspace.workspace.security.session.entity.TrustedDevice;
import org.SprintForge.modules.workspace.workspace.security.session.entity.WorkspaceSession;

public interface SessionService {
    WorkspaceSession createSession(Long workspaceId, Long userId, String ipAddress, String userAgent, String deviceType);
    void refreshSession(Long sessionId);
    void expireSession(Long sessionId);
    TrustedDevice trustDevice(Long workspaceId, Long userId, String fingerprint, String deviceName, String deviceType);
    void revokeTrustedDevice(Long deviceId);
    boolean isSessionActive(String sessionToken);
}
