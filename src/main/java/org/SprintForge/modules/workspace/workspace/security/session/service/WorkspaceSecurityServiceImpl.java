package org.SprintForge.modules.workspace.workspace.security.session.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.workspace.security.session.dto.response.TrustedDeviceResponse;
import org.SprintForge.modules.workspace.workspace.security.session.dto.response.WorkspaceSessionResponse;
import org.SprintForge.modules.workspace.workspace.security.session.entity.TrustedDevice;
import org.SprintForge.modules.workspace.workspace.security.session.entity.WorkspaceSession;
import org.SprintForge.modules.workspace.workspace.security.session.repository.TrustedDeviceRepository;
import org.SprintForge.modules.workspace.workspace.security.session.repository.WorkspaceSessionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceSecurityServiceImpl implements WorkspaceSecurityService {

    private final WorkspaceSessionRepository workspaceSessionRepository;
    private final TrustedDeviceRepository trustedDeviceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceSessionResponse> getActiveSessions(Long workspaceId, Long requesterId) {
        return workspaceSessionRepository.findByWorkspaceIdAndIsActiveTrue(workspaceId)
                .stream().map(this::mapSession).collect(Collectors.toList());
    }

    @Override
    public void revokeSession(Long workspaceId, Long sessionId, Long actorId) {
        WorkspaceSession session = workspaceSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        if (!session.getWorkspaceId().equals(workspaceId)) {
            throw new ResourceNotFoundException("Session not found in workspace");
        }
        session.setIsActive(false);
        workspaceSessionRepository.save(session);
    }

    @Override
    public void revokeAllSessions(Long workspaceId, Long userId, Long actorId) {
        List<WorkspaceSession> sessions = workspaceSessionRepository.findByUserIdAndWorkspaceIdAndIsActiveTrue(userId, workspaceId);
        sessions.forEach(s -> s.setIsActive(false));
        workspaceSessionRepository.saveAll(sessions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceSessionResponse> getLoginHistory(Long workspaceId, Long userId, int page, int size) {
        return workspaceSessionRepository.findByUserIdAndIsActiveTrue(userId, PageRequest.of(page, size))
                .stream()
                .filter(s -> s.getWorkspaceId().equals(workspaceId))
                .map(this::mapSession).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrustedDeviceResponse> getTrustedDevices(Long workspaceId, Long userId) {
        return trustedDeviceRepository.findByWorkspaceIdAndUserIdAndIsActiveTrue(workspaceId, userId)
                .stream().map(this::mapDevice).collect(Collectors.toList());
    }

    @Override
    public void revokeTrustedDevice(Long workspaceId, Long deviceId, Long actorId) {
        TrustedDevice device = trustedDeviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        if (!device.getWorkspaceId().equals(workspaceId)) {
            throw new ResourceNotFoundException("Device not found in workspace");
        }
        device.setIsActive(false);
        trustedDeviceRepository.save(device);
    }

    private WorkspaceSessionResponse mapSession(WorkspaceSession s) {
        return WorkspaceSessionResponse.builder()
                .id(s.getId())
                .workspaceId(s.getWorkspaceId())
                .userId(s.getUserId())
                .ipAddress(s.getIpAddress())
                .userAgent(s.getUserAgent())
                .deviceType(s.getDeviceType())
                .location(s.getLocation())
                .isActive(s.getIsActive())
                .lastActivityAt(s.getLastActivityAt())
                .expiresAt(s.getExpiresAt())
                .createdAt(s.getCreatedAt())
                .build();
    }

    private TrustedDeviceResponse mapDevice(TrustedDevice d) {
        return TrustedDeviceResponse.builder()
                .id(d.getId())
                .workspaceId(d.getWorkspaceId())
                .userId(d.getUserId())
                .deviceFingerprint(d.getDeviceFingerprint())
                .deviceName(d.getDeviceName())
                .deviceType(d.getDeviceType())
                .trustedAt(d.getTrustedAt())
                .lastSeenAt(d.getLastSeenAt())
                .isActive(d.getIsActive())
                .build();
    }
}
