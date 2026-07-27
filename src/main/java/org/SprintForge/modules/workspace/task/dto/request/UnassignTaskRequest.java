package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnassignTaskRequest {

    @NotNull(message = "Project member ID is required")
    private Long projectMemberId;
}
