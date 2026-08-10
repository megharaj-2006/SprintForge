package org.SprintForge.modules.workflow.nodes.logic;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class StopNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("stop.logic")
                .displayName("Stop Logic")
                .category("LOGIC")
                .icon("square")
                .description("Terminates workflow execution cleanly.")
                .branches(List.of())
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        return NodeExecutionResult.success(Map.of("terminated", true, "stoppedAt", System.currentTimeMillis()));
    }
}
