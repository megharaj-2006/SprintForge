package org.SprintForge.modules.workflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateWorkflowRequest {
    private Long workspaceId;

    @NotBlank(message = "Workflow name is required")
    private String name;

    private String description;
    private String category;
}
