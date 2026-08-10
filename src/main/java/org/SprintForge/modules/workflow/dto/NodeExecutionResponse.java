package org.SprintForge.modules.workflow.dto;

import lombok.Data;
import org.SprintForge.modules.workflow.entity.enums.NodeExecutionStatus;

import java.time.LocalDateTime;

@Data
public class NodeExecutionResponse {
    private Long id;
    private Long workflowExecutionId;
    private Long workflowNodeId;
    private String nodeName;
    private String nodeType;
    private NodeExecutionStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Long duration;
    private String input;
    private String output;
    private String error;
}
