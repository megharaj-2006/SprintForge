package org.SprintForge.modules.workflow.dto;

import lombok.Data;
import org.SprintForge.modules.workflow.entity.enums.BranchLabel;

import java.time.LocalDateTime;

@Data
public class WorkflowConnectionResponse {
    private Long id;
    private Long sourceNodeId;
    private Long targetNodeId;
    private BranchLabel branchLabel;
    private Integer priority;
    private String conditionExpression;
    private LocalDateTime createdAt;
}
