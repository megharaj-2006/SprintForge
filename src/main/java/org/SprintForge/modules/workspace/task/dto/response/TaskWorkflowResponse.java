package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskWorkflowResponse {
    private Long taskId;
    private TaskStatus currentStatus;
    private List<TaskStatus> allowedTransitions;
}
