package org.SprintForge.modules.workflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CloneWorkflowRequest {
    @NotBlank(message = "New workflow name is required")
    private String newName;
    private Long targetWorkspaceId;
}
