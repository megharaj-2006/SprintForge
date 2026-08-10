package org.SprintForge.modules.workspace.workspace.security.audit.service;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceAuditLog;
import org.SprintForge.modules.workspace.workspace.security.audit.dto.response.WorkspaceAuditLogResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkspaceAuditService {
    WorkspaceAuditLog logEvent(Long workspaceId, Long actorId, String action, String entityType, Long entityId, String oldValue, String newValue, String ipAddress, String userAgent);
    List<WorkspaceAuditLogResponse> getAuditLog(Long workspaceId, int page, int size);
    List<WorkspaceAuditLogResponse> filterByActor(Long workspaceId, Long actorId, int page, int size);
    List<WorkspaceAuditLogResponse> filterByAction(Long workspaceId, String action, int page, int size);
    List<WorkspaceAuditLogResponse> filterByEntityType(Long workspaceId, String entityType, int page, int size);
    List<WorkspaceAuditLogResponse> filterByDateRange(Long workspaceId, LocalDateTime from, LocalDateTime to, int page, int size);
}
