package org.SprintForge.modules.workflow.governance;

public interface WorkflowGovernanceService {
    void validatePublishPermission(Long workflowId);
    void validateExecutionQuotas(Long workflowId, Long workspaceId);
    WorkflowPolicy createOrUpdatePolicy(WorkflowPolicy policy);
}
