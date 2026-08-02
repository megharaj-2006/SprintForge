package org.SprintForge.modules.workspace.project.governance.change.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.change.entity.enums.ChangeStatus;
import org.SprintForge.modules.workspace.project.governance.change.entity.enums.ChangeType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeResponse {

    private Long id;
    private Long projectId;
    private ChangeType changeType;
    private String title;
    private String description;
    private String reason;
    private String impact;
    private Long requestedById;
    private Long approvedById;
    private Long implementedById;
    private ChangeStatus status;
    private LocalDateTime completedAt;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
