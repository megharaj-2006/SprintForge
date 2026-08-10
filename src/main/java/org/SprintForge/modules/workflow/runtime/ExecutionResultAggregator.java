package org.SprintForge.modules.workflow.runtime;

import org.SprintForge.modules.workflow.executor.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExecutionResultAggregator {

    public Map<String, Object> aggregateResults(ExecutionContext context) {
        Map<String, Object> result = new HashMap<>();
        result.put("executionId", context.getExecutionId());
        result.put("correlationId", context.getCorrelationId());
        result.put("outputs", context.getNodeOutputs());
        result.put("variables", context.getVariables());
        return result;
    }
}
