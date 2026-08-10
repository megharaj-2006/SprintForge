package org.SprintForge.modules.workflow.service;

import org.SprintForge.modules.workflow.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkflowManagementService {
    WorkflowResponse createWorkflow(CreateWorkflowRequest request);
    WorkflowResponse updateWorkflow(Long id, UpdateWorkflowRequest request);
    WorkflowResponse archiveWorkflow(Long id);
    WorkflowResponse restoreWorkflow(Long id);
    WorkflowResponse cloneWorkflow(Long id, CloneWorkflowRequest request);
    WorkflowResponse publishWorkflow(Long id, PublishWorkflowRequest request);
    WorkflowResponse getWorkflowById(Long id);
    Page<WorkflowSummaryResponse> getWorkflows(Long workspaceId, Pageable pageable);
    void deleteWorkflow(Long id);
}
