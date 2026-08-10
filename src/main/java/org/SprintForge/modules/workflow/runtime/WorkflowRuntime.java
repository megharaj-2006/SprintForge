package org.SprintForge.modules.workflow.runtime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessException;
import org.SprintForge.modules.workflow.dto.WorkflowValidationResponse;
import org.SprintForge.modules.workflow.entity.WorkflowExecution;
import org.SprintForge.modules.workflow.entity.WorkflowVersion;
import org.SprintForge.modules.workflow.event.WorkflowExecutionCompletedEvent;
import org.SprintForge.modules.workflow.event.WorkflowExecutionFailedEvent;
import org.SprintForge.modules.workflow.event.WorkflowExecutionStartedEvent;
import org.SprintForge.modules.workflow.executor.ExecutionContext;
import org.SprintForge.modules.workflow.graph.GraphBuilder;
import org.SprintForge.modules.workflow.graph.GraphValidator;
import org.SprintForge.modules.workflow.graph.WorkflowGraph;
import org.SprintForge.modules.workflow.repository.WorkflowExecutionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkflowRuntime {

    private final GraphBuilder graphBuilder;
    private final GraphValidator graphValidator;
    private final ExecutionContextManager contextManager;
    private final VariableResolver variableResolver;
    private final ExecutionPipeline executionPipeline;
    private final ExecutionStateManager executionStateManager;
    private final ExecutionResultAggregator resultAggregator;
    private final WorkflowExecutionRepository executionRepository;
    private final ApplicationEventPublisher eventPublisher;

    public WorkflowExecution runWorkflow(WorkflowVersion version, String triggeredBy, String triggerSource, Map<String, Object> inputVariables) {
        log.info("Starting workflow execution for version ID {}", version.getId());

        // 1. Build & validate graph
        WorkflowGraph graph = graphBuilder.buildGraph(version);
        WorkflowValidationResponse validation = graphValidator.validate(graph);
        if (!validation.isValid()) {
            throw new BusinessException("Cannot execute invalid workflow version: " + validation.getErrors());
        }

        // 2. Create execution record
        WorkflowExecution execution = new WorkflowExecution();
        execution.setWorkflowVersion(version);
        execution.setTriggeredBy(triggeredBy);
        execution.setTriggerSource(triggerSource);
        executionRepository.save(execution);

        // 3. Update execution started & publish event
        executionStateManager.updateExecutionStarted(execution);
        eventPublisher.publishEvent(new WorkflowExecutionStartedEvent(execution.getId(), version.getId(), triggeredBy));

        // 4. Initialize ExecutionContext & Resolve Variables
        ExecutionContext context = contextManager.createExecutionContext(execution, inputVariables);
        variableResolver.resolveVariables(version, context, inputVariables);

        try {
            // 5. Execute Pipeline
            executionPipeline.executeGraph(graph, execution, context);

            // 6. Aggregate results & finish
            Map<String, Object> finalResult = resultAggregator.aggregateResults(context);
            executionStateManager.updateExecutionCompleted(execution, finalResult);
            contextManager.saveState(execution, context);

            eventPublisher.publishEvent(new WorkflowExecutionCompletedEvent(
                    execution.getId(), version.getId(), execution.getDuration(), execution.getResult()
            ));

            return execution;
        } catch (Exception e) {
            log.error("Workflow execution failed for execution ID {}: {}", execution.getId(), e.getMessage(), e);
            executionStateManager.updateExecutionFailed(execution, e.getMessage());
            contextManager.saveState(execution, context);

            eventPublisher.publishEvent(new WorkflowExecutionFailedEvent(
                    execution.getId(), version.getId(), e.getMessage()
            ));

            return execution;
        }
    }
}
