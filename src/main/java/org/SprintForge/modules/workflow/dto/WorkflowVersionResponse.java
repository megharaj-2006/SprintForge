package org.SprintForge.modules.workflow.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkflowVersionResponse {
    private Long id;
    private Long workflowId;
    private Integer versionNumber;
    private Boolean published;
    private LocalDateTime publishedAt;
    private String changeSummary;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<WorkflowNodeResponse> nodes;
    private List<WorkflowConnectionResponse> connections;
    private List<WorkflowVariableResponse> variables;
}
