package org.SprintForge.modules.workflow.executor.impl;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.SprintForge.modules.workflow.executor.ExecutionContext;
import org.SprintForge.modules.workflow.executor.NodeExecutor;
import org.SprintForge.modules.workflow.executor.NodeExecutorResult;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class DelayExecutor implements NodeExecutor {

    @Override
    public String getNodeKey() {
        return "delay.logic";
    }

    @Override
    public NodeExecutorResult execute(ExecutionContext context, WorkflowNode node) {
        log.info("Executing DelayExecutor for node {}", node.getName());
        return NodeExecutorResult.success(Map.of("delayApplied", true));
    }
}
