package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusHistoryResponse {
    private Long taskId;
    private TaskStatus oldStatus;
    private TaskStatus newStatus;
    private Long changedBy;
    private LocalDateTime changedAt;
}
