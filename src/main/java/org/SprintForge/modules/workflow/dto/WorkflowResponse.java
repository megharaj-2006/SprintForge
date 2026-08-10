package org.SprintForge.modules.workflow.dto;

import lombok.Data;
import org.SprintForge.modules.workflow.entity.enums.WorkflowStatus;

import java.time.LocalDateTime;

@Data
public class WorkflowResponse {
    private Long id;
    private Long workspaceId;
    private String name;
    private String description;
    private String category;
    private WorkflowStatus status;
    private Boolean enabled;
    private Boolean published;
    private Boolean archived;
    private Long currentVersionId;
    private Integer currentVersionNumber;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
