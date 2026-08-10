package org.SprintForge.modules.workflow.service;

import org.SprintForge.modules.workflow.dto.*;

import java.util.List;

public interface WorkflowVersionService {
    WorkflowVersionResponse createVersion(Long workflowId, String changeSummary);
    WorkflowVersionResponse publishVersion(Long versionId, String changeSummary);
    WorkflowVersionResponse rollbackVersion(Long workflowId, Integer targetVersionNumber);
    WorkflowVersionResponse getVersionById(Long versionId);
    List<WorkflowVersionResponse> getVersionsByWorkflowId(Long workflowId);
    WorkflowVersionResponse addNode(Long versionId, CreateNodeRequest request);
    WorkflowVersionResponse addConnection(Long versionId, CreateConnectionRequest request);
    WorkflowVersionResponse addVariable(Long versionId, CreateVariableRequest request);
}
