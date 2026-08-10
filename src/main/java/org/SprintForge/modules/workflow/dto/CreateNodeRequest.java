package org.SprintForge.modules.workflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateNodeRequest {
    @NotNull(message = "Definition ID is required")
    private Long definitionId;

    @NotBlank(message = "Node name is required")
    private String name;

    @NotBlank(message = "Node type is required")
    private String nodeType;

    private Double positionX = 0.0;
    private Double positionY = 0.0;
    private Boolean enabled = true;
}
