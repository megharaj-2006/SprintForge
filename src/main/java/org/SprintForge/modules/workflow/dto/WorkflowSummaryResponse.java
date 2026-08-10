package org.SprintForge.modules.workflow.dto;

import lombok.Data;
import org.SprintForge.modules.workflow.entity.enums.WorkflowStatus;

import java.time.LocalDateTime;

@Data
public class WorkflowSummaryResponse {
    private Long id;
    private String name;
    private String category;
    private WorkflowStatus status;
    private Boolean enabled;
    private Boolean published;
    private Integer currentVersionNumber;
    private LocalDateTime updatedAt;
}
