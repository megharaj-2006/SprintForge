package org.SprintForge.modules.workflow.dto;

import lombok.Data;
import org.SprintForge.modules.workflow.entity.enums.VariableScope;

@Data
public class WorkflowVariableResponse {
    private Long id;
    private String name;
    private String type;
    private VariableScope scope;
    private String defaultValue;
    private Boolean required;
}
