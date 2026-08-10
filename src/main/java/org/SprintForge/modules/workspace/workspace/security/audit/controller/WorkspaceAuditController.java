package org.SprintForge.modules.workspace.workspace.security.audit.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.security.audit.dto.response.WorkspaceAuditLogResponse;
import org.SprintForge.modules.workspace.workspace.security.audit.service.WorkspaceAuditService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workspaces/{workspaceId}/audit")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Tag(name = "Workspace Audit")
public class WorkspaceAuditController {

    private final WorkspaceAuditService workspaceAuditService;

    @GetMapping
    public List<WorkspaceAuditLogResponse> getAuditLog(
            @PathVariable Long workspaceId,
            @RequestParam(required = false) Long actorId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        if (actorId != null) {
            return workspaceAuditService.filterByActor(workspaceId, actorId, page, size);
        } else if (action != null) {
            return workspaceAuditService.filterByAction(workspaceId, action, page, size);
        } else if (entityType != null) {
            return workspaceAuditService.filterByEntityType(workspaceId, entityType, page, size);
        } else if (from != null && to != null) {
            return workspaceAuditService.filterByDateRange(workspaceId, from, to, page, size);
        } else {
            return workspaceAuditService.getAuditLog(workspaceId, page, size);
        }
    }
}
