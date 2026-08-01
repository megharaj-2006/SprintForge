package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskPriority;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkPriorityRequest {

    @NotEmpty(message = "Task IDs are required")
    private List<Long> taskIds;

    @NotNull(message = "Priority is required")
    private TaskPriority priority;
}
