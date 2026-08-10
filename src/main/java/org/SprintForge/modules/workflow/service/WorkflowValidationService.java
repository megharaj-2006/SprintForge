package org.SprintForge.modules.workflow.service;

import org.SprintForge.modules.workflow.dto.WorkflowValidationResponse;

public interface WorkflowValidationService {
    WorkflowValidationResponse validateWorkflow(Long workflowId, Long versionId);
}
