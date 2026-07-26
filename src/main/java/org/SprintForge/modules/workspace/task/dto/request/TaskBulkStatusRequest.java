package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskBulkStatusRequest {

    @NotEmpty(message = "Task IDs list cannot be empty")
    private List<Long> taskIds;

    @NotNull(message = "Status ID is required")
    private Long statusId;
}
