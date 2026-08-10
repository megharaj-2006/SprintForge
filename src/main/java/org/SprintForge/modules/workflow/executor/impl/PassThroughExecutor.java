package org.SprintForge.modules.workflow.executor.impl;

import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.SprintForge.modules.workflow.executor.ExecutionContext;
import org.SprintForge.modules.workflow.executor.NodeExecutor;
import org.SprintForge.modules.workflow.executor.NodeExecutorResult;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PassThroughExecutor implements NodeExecutor {

    @Override
    public String getNodeKey() {
        return "passthrough.logic";
    }

    @Override
    public NodeExecutorResult execute(ExecutionContext context, WorkflowNode node) {
        return NodeExecutorResult.success(Map.of("passthrough", true));
    }
}
