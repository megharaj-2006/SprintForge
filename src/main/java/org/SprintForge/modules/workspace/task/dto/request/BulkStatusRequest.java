package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class BulkStatusRequest {

    @NotEmpty(message = "Task IDs are required")
    private List<Long> taskIds;

    @NotNull(message = "Status is required")
    private TaskStatus status;
}
