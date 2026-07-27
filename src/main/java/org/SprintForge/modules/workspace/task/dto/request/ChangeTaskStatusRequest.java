package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTaskStatusRequest {

    @NotNull(message = "Status is required")
    private TaskStatus status;
}
