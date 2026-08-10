package org.SprintForge.modules.workflow.executor;

import org.SprintForge.modules.workflow.entity.WorkflowNode;

public interface NodeExecutor {

    String getNodeKey();

    default void initialize(ExecutionContext context, WorkflowNode node) {
        // Default no-op
    }

    default void validate(ExecutionContext context, WorkflowNode node) {
        // Default no-op
    }

    NodeExecutorResult execute(ExecutionContext context, WorkflowNode node);

    default void rollback(ExecutionContext context, WorkflowNode node) {
        // Default no-op
    }

    default void cleanup(ExecutionContext context, WorkflowNode node) {
        // Default no-op
    }
}
