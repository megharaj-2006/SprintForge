package org.SprintForge.modules.workflow.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.SprintForge.modules.workflow.entity.ExecutionContextEntity;
import org.SprintForge.modules.workflow.entity.WorkflowExecution;
import org.SprintForge.modules.workflow.executor.ExecutionContext;
import org.SprintForge.modules.workflow.repository.ExecutionContextRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExecutionContextManager {

    private final ExecutionContextRepository executionContextRepository;
    private final ObjectMapper objectMapper;

    public ExecutionContext createExecutionContext(WorkflowExecution execution, Map<String, Object> initialVariables) {
        String correlationId = UUID.randomUUID().toString();
        ExecutionContext context = new ExecutionContext(String.valueOf(execution.getId()), correlationId);

        if (initialVariables != null) {
            initialVariables.forEach(context::setGlobalVariable);
        }

        saveState(execution, context);
        return context;
    }

    @SneakyThrows
    public void saveState(WorkflowExecution execution, ExecutionContext context) {
        ExecutionContextEntity entity = executionContextRepository.findByWorkflowExecutionId(execution.getId())
                .orElseGet(() -> {
                    ExecutionContextEntity e = new ExecutionContextEntity();
                    e.setWorkflowExecution(execution);
                    return e;
                });

        entity.setVariables(objectMapper.writeValueAsString(context.getVariables()));
        entity.setRuntimeState(objectMapper.writeValueAsString(context.getNodeOutputs()));
        entity.setMetadata(objectMapper.writeValueAsString(context.getMetadata()));
        entity.setCorrelationId(context.getCorrelationId());

        executionContextRepository.save(entity);
    }
}
