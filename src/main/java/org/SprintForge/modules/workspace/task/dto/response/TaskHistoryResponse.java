package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskHistoryActionType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskHistoryResponse {
    private Long id;
    private Long taskId;
    private Long performedById;
    private String performedByUsername;
    private String performedByName;
    private TaskHistoryActionType actionType;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private String description;
    private LocalDateTime createdAt;
}
