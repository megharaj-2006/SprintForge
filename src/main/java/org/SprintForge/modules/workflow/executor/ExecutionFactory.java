package org.SprintForge.modules.workflow.executor;

import org.SprintForge.modules.workflow.entity.NodeExecution;
import org.SprintForge.modules.workflow.entity.WorkflowExecution;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.SprintForge.modules.workflow.entity.enums.NodeExecutionStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExecutionFactory {

    public NodeExecution createNodeExecution(WorkflowExecution workflowExecution, WorkflowNode node) {
        NodeExecution nodeExecution = new NodeExecution();
        nodeExecution.setWorkflowExecution(workflowExecution);
        nodeExecution.setWorkflowNode(node);
        nodeExecution.setStatus(NodeExecutionStatus.PENDING);
        nodeExecution.setStartedAt(LocalDateTime.now());
        return nodeExecution;
    }
}
