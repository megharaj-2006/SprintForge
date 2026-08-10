package org.SprintForge.modules.workflow.validation;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workflow.dto.WorkflowValidationResponse;
import org.SprintForge.modules.workflow.entity.Workflow;
import org.SprintForge.modules.workflow.entity.WorkflowVersion;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkflowValidator {

    private final VersionValidator versionValidator;

    public WorkflowValidationResponse validate(Workflow workflow, WorkflowVersion version) {
        WorkflowValidationResponse response = versionValidator.validateVersion(version);

        if (workflow.getName() == null || workflow.getName().isBlank()) {
            response.addError("BLANK_WORKFLOW_NAME", "Workflow name cannot be blank", String.valueOf(workflow.getId()));
        }

        return response;
    }
}
