package org.SprintForge.modules.workflow.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.SprintForge.modules.workflow.entity.NodeExecution;
import org.SprintForge.modules.workflow.entity.WorkflowExecution;
import org.SprintForge.modules.workflow.entity.enums.ExecutionStatus;
import org.SprintForge.modules.workflow.entity.enums.NodeExecutionStatus;
import org.SprintForge.modules.workflow.repository.NodeExecutionRepository;
import org.SprintForge.modules.workflow.repository.WorkflowExecutionRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ExecutionStateManager {

    private final WorkflowExecutionRepository workflowExecutionRepository;
    private final NodeExecutionRepository nodeExecutionRepository;
    private final ObjectMapper objectMapper;

    public void updateExecutionStarted(WorkflowExecution execution) {
        execution.setStatus(ExecutionStatus.RUNNING);
        execution.setStartedAt(LocalDateTime.now());
        workflowExecutionRepository.save(execution);
    }

    @SneakyThrows
    public void updateExecutionCompleted(WorkflowExecution execution, Object resultData) {
        execution.setStatus(ExecutionStatus.COMPLETED);
        execution.setCompletedAt(LocalDateTime.now());
        if (execution.getStartedAt() != null) {
            execution.setDuration(Duration.between(execution.getStartedAt(), execution.getCompletedAt()).toMillis());
        }
        if (resultData != null) {
            execution.setResult(objectMapper.writeValueAsString(resultData));
        }
        workflowExecutionRepository.save(execution);
    }

    public void updateExecutionFailed(WorkflowExecution execution, String errorMessage) {
        execution.setStatus(ExecutionStatus.FAILED);
        execution.setCompletedAt(LocalDateTime.now());
        if (execution.getStartedAt() != null) {
            execution.setDuration(Duration.between(execution.getStartedAt(), execution.getCompletedAt()).toMillis());
        }
        execution.setError(errorMessage);
        workflowExecutionRepository.save(execution);
    }

    @SneakyThrows
    public void updateNodeExecutionCompleted(NodeExecution nodeExecution, Object outputData) {
        nodeExecution.setStatus(NodeExecutionStatus.COMPLETED);
        nodeExecution.setCompletedAt(LocalDateTime.now());
        if (nodeExecution.getStartedAt() != null) {
            nodeExecution.setDuration(Duration.between(nodeExecution.getStartedAt(), nodeExecution.getCompletedAt()).toMillis());
        }
        if (outputData != null) {
            nodeExecution.setOutput(objectMapper.writeValueAsString(outputData));
        }
        nodeExecutionRepository.save(nodeExecution);
    }

    public void updateNodeExecutionFailed(NodeExecution nodeExecution, String errorMessage) {
        nodeExecution.setStatus(NodeExecutionStatus.FAILED);
        nodeExecution.setCompletedAt(LocalDateTime.now());
        if (nodeExecution.getStartedAt() != null) {
            nodeExecution.setDuration(Duration.between(nodeExecution.getStartedAt(), nodeExecution.getCompletedAt()).toMillis());
        }
        nodeExecution.setError(errorMessage);
        nodeExecutionRepository.save(nodeExecution);
    }
}
