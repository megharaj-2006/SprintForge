package org.SprintForge.modules.workflow.service;

import org.SprintForge.modules.workflow.dto.WorkflowMonitoringResponse;
import org.SprintForge.modules.workflow.dto.WorkflowSummaryResponse;

import java.util.List;

public interface WorkflowMonitoringService {
    WorkflowMonitoringResponse getWorkflowMonitoring(Long workflowId);
    List<WorkflowSummaryResponse> getMostExecutedWorkflows(int limit);
}
