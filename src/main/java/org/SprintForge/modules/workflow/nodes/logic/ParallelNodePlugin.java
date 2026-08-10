package org.SprintForge.modules.workflow.nodes.logic;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ParallelNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("parallel.logic")
                .displayName("Parallel Logic")
                .category("LOGIC")
                .icon("share-2")
                .description("Forks workflow execution into parallel concurrently running branches.")
                .branches(List.of(
                        NodeBranchPort.builder().label("DEFAULT").description("Forks into all connected downstream outgoing connections").build()
                ))
                .capabilities(NodeCapabilities.builder().parallelSafe(true).async(true).build())
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        return NodeExecutionResult.success(Map.of("forkedAt", System.currentTimeMillis(), "parallelMode", true));
    }
}
