package org.SprintForge.modules.workspace.project.governance.approval.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.approval.entity.enums.ApprovalStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalResponse {

    private Long id;
    private Long projectId;
    private String entityType;
    private Long entityId;
    private String title;
    private ApprovalStatus status;
    private Long requestedById;
    private LocalDateTime requestedAt;
    private Long approvedById;
    private LocalDateTime approvedAt;
    private String comments;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
