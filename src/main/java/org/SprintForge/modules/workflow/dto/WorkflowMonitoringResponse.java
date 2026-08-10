package org.SprintForge.modules.workflow.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowMonitoringResponse {
    private Long workflowId;
    private long totalExecutions;
    private long successfulExecutions;
    private long failedExecutions;
    private double successRate;
    private double failureRate;
    private double averageExecutionTimeMillis;
    private List<WorkflowExecutionResponse> recentExecutions;
}
