package org.SprintForge.modules.workspace.workspace.security.audit.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceAuditLog;
import org.SprintForge.modules.workspace.workspace.security.audit.dto.response.WorkspaceAuditLogResponse;
import org.SprintForge.modules.workspace.workspace.security.audit.repository.WorkspaceAuditRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceAuditServiceImpl implements WorkspaceAuditService {

    private final WorkspaceAuditRepository workspaceAuditRepository;

    @Override
    public WorkspaceAuditLog logEvent(Long workspaceId, Long actorId, String action, String entityType, Long entityId, String oldValue, String newValue, String ipAddress, String userAgent) {
        WorkspaceAuditLog log = WorkspaceAuditLog.builder()
                .workspaceId(workspaceId)
                .actorId(actorId)
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .oldValue(oldValue)
                .newValue(newValue)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .build();
        return workspaceAuditRepository.save(log);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceAuditLogResponse> getAuditLog(Long workspaceId, int page, int size) {
        return workspaceAuditRepository.findByWorkspaceIdOrderByCreatedAtDesc(workspaceId, PageRequest.of(page, size))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceAuditLogResponse> filterByActor(Long workspaceId, Long actorId, int page, int size) {
        return workspaceAuditRepository.findByWorkspaceIdAndActorId(workspaceId, actorId, PageRequest.of(page, size))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceAuditLogResponse> filterByAction(Long workspaceId, String action, int page, int size) {
        return workspaceAuditRepository.findByWorkspaceIdAndAction(workspaceId, action, PageRequest.of(page, size))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceAuditLogResponse> filterByEntityType(Long workspaceId, String entityType, int page, int size) {
        return workspaceAuditRepository.findByWorkspaceIdAndEntityType(workspaceId, entityType, PageRequest.of(page, size))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceAuditLogResponse> filterByDateRange(Long workspaceId, LocalDateTime from, LocalDateTime to, int page, int size) {
        return workspaceAuditRepository.findByDateRange(workspaceId, from, to, PageRequest.of(page, size))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private WorkspaceAuditLogResponse mapToResponse(WorkspaceAuditLog log) {
        return WorkspaceAuditLogResponse.builder()
                .id(log.getId())
                .workspaceId(log.getWorkspaceId())
                .actorId(log.getActorId())
                .action(log.getAction())
                .entityType(log.getEntityType())
                .entityId(log.getEntityId())
                .oldValue(log.getOldValue())
                .newValue(log.getNewValue())
                .ipAddress(log.getIpAddress())
                .userAgent(log.getUserAgent())
                .createdAt(log.getCreatedAt())
                .build();
    }
}
