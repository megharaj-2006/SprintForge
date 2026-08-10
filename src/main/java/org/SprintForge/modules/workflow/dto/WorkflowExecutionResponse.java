package org.SprintForge.modules.workflow.dto;

import lombok.Data;
import org.SprintForge.modules.workflow.entity.enums.ExecutionStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkflowExecutionResponse {
    private Long id;
    private Long workflowVersionId;
    private Long workflowId;
    private ExecutionStatus status;
    private String triggeredBy;
    private String triggerSource;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Long duration;
    private Long currentNodeId;
    private String contextId;
    private String result;
    private String error;
    private List<NodeExecutionResponse> nodeExecutions;
}
