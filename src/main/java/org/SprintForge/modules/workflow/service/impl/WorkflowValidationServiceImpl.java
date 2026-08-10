package org.SprintForge.modules.workflow.service.impl;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workflow.dto.WorkflowValidationResponse;
import org.SprintForge.modules.workflow.entity.Workflow;
import org.SprintForge.modules.workflow.entity.WorkflowVersion;
import org.SprintForge.modules.workflow.repository.WorkflowRepository;
import org.SprintForge.modules.workflow.repository.WorkflowVersionRepository;
import org.SprintForge.modules.workflow.service.WorkflowValidationService;
import org.SprintForge.modules.workflow.validation.WorkflowValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkflowValidationServiceImpl implements WorkflowValidationService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowVersionRepository workflowVersionRepository;
    private final WorkflowValidator workflowValidator;

    @Override
    @Transactional(readOnly = true)
    public WorkflowValidationResponse validateWorkflow(Long workflowId, Long versionId) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found with id: " + workflowId));

        WorkflowVersion version = versionId != null ?
                workflowVersionRepository.findById(versionId)
                        .orElseThrow(() -> new ResourceNotFoundException("WorkflowVersion not found with id: " + versionId)) :
                workflow.getCurrentVersion();

        if (version == null) {
            WorkflowValidationResponse response = new WorkflowValidationResponse();
            response.addError("MISSING_VERSION", "Workflow has no current version to validate", String.valueOf(workflowId));
            return response;
        }

        return workflowValidator.validate(workflow, version);
    }
}
