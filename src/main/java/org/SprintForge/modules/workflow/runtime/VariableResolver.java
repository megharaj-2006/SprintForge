package org.SprintForge.modules.workflow.runtime;

import org.SprintForge.modules.workflow.entity.WorkflowVariable;
import org.SprintForge.modules.workflow.entity.WorkflowVersion;
import org.SprintForge.modules.workflow.entity.enums.VariableScope;
import org.SprintForge.modules.workflow.executor.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class VariableResolver {

    public void resolveVariables(WorkflowVersion version, ExecutionContext context, Map<String, Object> inputVariables) {
        if (version.getVariables() != null) {
            for (WorkflowVariable var : version.getVariables()) {
                Object val = (inputVariables != null && inputVariables.containsKey(var.getName())) ?
                        inputVariables.get(var.getName()) : var.getDefaultValue();

                if (val != null) {
                    if (var.getScope() == VariableScope.NODE) {
                        context.setNodeVariable("DEFAULT", var.getName(), val);
                    } else if (var.getScope() == VariableScope.EXECUTION) {
                        context.setExecutionVariable(var.getName(), val);
                    } else {
                        context.setGlobalVariable(var.getName(), val);
                    }
                }
            }
        }

        if (inputVariables != null) {
            inputVariables.forEach((k, v) -> {
                if (context.getExecutionVariables().containsKey(k)) {
                    context.setExecutionVariable(k, v);
                } else {
                    context.setGlobalVariable(k, v);
                }
            });
        }
    }
}
