package org.SprintForge.modules.workflow.service;

import org.SprintForge.modules.workflow.dto.ExecuteWorkflowRequest;
import org.SprintForge.modules.workflow.dto.WorkflowExecutionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkflowExecutionService {
    WorkflowExecutionResponse executeWorkflow(Long workflowId, ExecuteWorkflowRequest request);
    WorkflowExecutionResponse cancelExecution(Long executionId);
    WorkflowExecutionResponse retryExecution(Long executionId);
    WorkflowExecutionResponse resumeExecution(Long executionId);
    WorkflowExecutionResponse getExecutionById(Long executionId);
    Page<WorkflowExecutionResponse> getExecutionHistory(Long workflowId, Pageable pageable);
}
