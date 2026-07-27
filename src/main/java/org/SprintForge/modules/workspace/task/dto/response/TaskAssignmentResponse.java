package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssignmentResponse {
    private Long id;
    private Long taskId;
    private Long projectMemberId;
    private Long assignedBy;
    private LocalDateTime assignedAt;
}
