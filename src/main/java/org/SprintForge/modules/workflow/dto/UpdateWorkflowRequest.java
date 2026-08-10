package org.SprintForge.modules.workflow.dto;

import lombok.Data;
import org.SprintForge.modules.workflow.entity.enums.WorkflowStatus;

@Data
public class UpdateWorkflowRequest {
    private String name;
    private String description;
    private String category;
    private WorkflowStatus status;
    private Boolean enabled;
}
