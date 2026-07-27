package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskDependencyType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDependencyResponse {
    private Long id;
    private Long predecessorTaskId;
    private Long successorTaskId;
    private TaskDependencyType type;
    private String createdBy;
    private LocalDateTime createdAt;
}
