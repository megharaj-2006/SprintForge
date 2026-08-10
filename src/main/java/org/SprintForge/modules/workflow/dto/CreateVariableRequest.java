package org.SprintForge.modules.workflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.SprintForge.modules.workflow.entity.enums.VariableScope;

@Data
public class CreateVariableRequest {
    @NotBlank(message = "Variable name is required")
    private String name;

    private String type = "STRING";
    private VariableScope scope = VariableScope.GLOBAL;
    private String defaultValue;
    private Boolean required = false;
}
