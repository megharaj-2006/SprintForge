package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReassignTaskRequest {

    @NotEmpty(message = "Project member IDs list cannot be empty")
    private List<Long> projectMemberIds;
}
