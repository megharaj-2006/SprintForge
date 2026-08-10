package org.SprintForge.modules.workflow.executor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExecutionDispatcher {

    private final NodeExecutorRegistry executorRegistry;

    public NodeExecutorResult dispatch(ExecutionContext context, WorkflowNode node, ExecutionStrategy strategy) {
        String nodeKey = node.getDefinition().getNodeKey();
        log.info("Dispatching node '{}' (Key: {}) using strategy {}", node.getName(), nodeKey, strategy);

        NodeExecutor executor = executorRegistry.getExecutor(nodeKey);

        try {
            executor.initialize(context, node);
            executor.validate(context, node);

            NodeExecutorResult result = executor.execute(context, node);

            if ("SUCCESS".equalsIgnoreCase(result.getStatus()) && result.getOutputData() != null) {
                context.setNodeOutput(String.valueOf(node.getId()), result.getOutputData());
            } else if ("FAILURE".equalsIgnoreCase(result.getStatus())) {
                executor.rollback(context, node);
            }

            return result;
        } catch (Exception e) {
            log.error("Execution error for node '{}': {}", node.getName(), e.getMessage(), e);
            executor.rollback(context, node);
            return NodeExecutorResult.failure(e.getMessage());
        } finally {
            executor.cleanup(context, node);
        }
    }
}
