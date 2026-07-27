package org.SprintForge.modules.workspace.task.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemoveTaskDependencyRequest {
    private Long predecessorTaskId;
    private Long successorTaskId;
}
