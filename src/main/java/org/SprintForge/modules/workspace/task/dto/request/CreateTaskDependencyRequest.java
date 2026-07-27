package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskDependencyType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskDependencyRequest {

    @NotNull(message = "Predecessor task ID is required")
    private Long predecessorTaskId;

    @NotNull(message = "Successor task ID is required")
    private Long successorTaskId;

    @NotNull(message = "Dependency type is required")
    private TaskDependencyType type;
}
