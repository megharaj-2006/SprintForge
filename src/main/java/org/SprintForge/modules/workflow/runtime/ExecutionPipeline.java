package org.SprintForge.modules.workflow.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workflow.entity.NodeExecution;
import org.SprintForge.modules.workflow.entity.WorkflowConnection;
import org.SprintForge.modules.workflow.entity.WorkflowExecution;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.SprintForge.modules.workflow.executor.*;
import org.SprintForge.modules.workflow.graph.PathResolver;
import org.SprintForge.modules.workflow.graph.WorkflowGraph;
import org.SprintForge.modules.workflow.repository.NodeExecutionRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExecutionPipeline {

    private final PathResolver pathResolver;
    private final ExecutionDispatcher executionDispatcher;
    private final ExecutionFactory executionFactory;
    private final ExecutionStateManager executionStateManager;
    private final NodeExecutionRepository nodeExecutionRepository;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void executeGraph(WorkflowGraph graph, WorkflowExecution workflowExecution, ExecutionContext context) {
        Set<Long> executedNodeIds = new HashSet<>();
        Queue<WorkflowNode> queue = new LinkedList<>(graph.getStartNodes());

        while (!queue.isEmpty()) {
            WorkflowNode node = queue.poll();
            if (executedNodeIds.contains(node.getId())) {
                continue;
            }

            workflowExecution.setCurrentNode(node);
            NodeExecution nodeExecution = executionFactory.createNodeExecution(workflowExecution, node);

            // Record input variables for this node execution
            nodeExecution.setInput(objectMapper.writeValueAsString(context.getVariables()));
            nodeExecutionRepository.save(nodeExecution);

            NodeExecutorResult result = executionDispatcher.dispatch(context, node, ExecutionStrategy.SEQUENTIAL);

            if ("SUCCESS".equalsIgnoreCase(result.getStatus())) {
                executionStateManager.updateNodeExecutionCompleted(nodeExecution, result.getOutputData());
                executedNodeIds.add(node.getId());

                List<WorkflowConnection> nextConns = pathResolver.resolveNextConnections(graph, node.getId(), "SUCCESS", null);
                for (WorkflowConnection conn : nextConns) {
                    WorkflowNode targetNode = conn.getTargetNode();
                    if (!executedNodeIds.contains(targetNode.getId())) {
                        queue.add(targetNode);
                    }
                }
            } else {
                executionStateManager.updateNodeExecutionFailed(nodeExecution, result.getErrorMessage());
                throw new RuntimeException("Node execution failed for node '" + node.getName() + "': " + result.getErrorMessage());
            }
        }
    }
}
