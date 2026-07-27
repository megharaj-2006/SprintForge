package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskDependencyType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDependencySummaryResponse {
    private Long id;
    private Long predecessorTaskId;
    private String predecessorTitle;
    private Long successorTaskId;
    private String successorTitle;
    private TaskDependencyType type;
}
