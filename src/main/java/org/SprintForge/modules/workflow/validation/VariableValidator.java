package org.SprintForge.modules.workflow.validation;

import org.SprintForge.modules.workflow.dto.WorkflowValidationResponse;
import org.SprintForge.modules.workflow.entity.WorkflowVariable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class VariableValidator {

    public void validateVariables(List<WorkflowVariable> variables, WorkflowValidationResponse response) {
        if (variables == null) {
            return;
        }

        Set<String> seen = new HashSet<>();
        for (WorkflowVariable variable : variables) {
            if (variable.getName() == null || variable.getName().isBlank()) {
                response.addError("BLANK_VARIABLE_NAME", "Variable name cannot be blank", String.valueOf(variable.getId()));
            } else if (!seen.add(variable.getName())) {
                response.addError("DUPLICATE_VARIABLE_NAME", "Duplicate variable name found: '" + variable.getName() + "'", String.valueOf(variable.getId()));
            }
        }
    }
}
