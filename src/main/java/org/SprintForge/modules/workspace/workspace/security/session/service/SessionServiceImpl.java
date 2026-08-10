package org.SprintForge.modules.workspace.workspace.security.session.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.workspace.security.session.entity.TrustedDevice;
import org.SprintForge.modules.workspace.workspace.security.session.entity.WorkspaceSession;
import org.SprintForge.modules.workspace.workspace.security.session.repository.TrustedDeviceRepository;
import org.SprintForge.modules.workspace.workspace.security.session.repository.WorkspaceSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionServiceImpl implements SessionService {

    private final WorkspaceSessionRepository workspaceSessionRepository;
    private final TrustedDeviceRepository trustedDeviceRepository;

    @Override
    public WorkspaceSession createSession(Long workspaceId, Long userId, String ipAddress, String userAgent, String deviceType) {
        WorkspaceSession session = WorkspaceSession.builder()
                .workspaceId(workspaceId)
                .userId(userId)
                .sessionToken(UUID.randomUUID().toString())
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .deviceType(deviceType)
                .lastActivityAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(30))
                .isActive(true)
                .build();
        return workspaceSessionRepository.save(session);
    }

    @Override
    public void refreshSession(Long sessionId) {
        WorkspaceSession session = workspaceSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        session.setLastActivityAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusDays(30));
        workspaceSessionRepository.save(session);
    }

    @Override
    public void expireSession(Long sessionId) {
        WorkspaceSession session = workspaceSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        session.setIsActive(false);
        workspaceSessionRepository.save(session);
    }

    @Override
    public TrustedDevice trustDevice(Long workspaceId, Long userId, String fingerprint, String deviceName, String deviceType) {
        TrustedDevice device = trustedDeviceRepository.findByWorkspaceIdAndUserIdAndDeviceFingerprint(workspaceId, userId, fingerprint)
                .orElseGet(() -> TrustedDevice.builder()
                        .workspaceId(workspaceId)
                        .userId(userId)
                        .deviceFingerprint(fingerprint)
                        .deviceName(deviceName)
                        .deviceType(deviceType)
                        .trustedAt(LocalDateTime.now())
                        .build());
        device.setLastSeenAt(LocalDateTime.now());
        device.setIsActive(true);
        return trustedDeviceRepository.save(device);
    }

    @Override
    public void revokeTrustedDevice(Long deviceId) {
        TrustedDevice device = trustedDeviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        device.setIsActive(false);
        trustedDeviceRepository.save(device);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isSessionActive(String sessionToken) {
        return workspaceSessionRepository.findBySessionToken(sessionToken)
                .map(s -> s.getIsActive() && s.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElse(false);
    }
}
