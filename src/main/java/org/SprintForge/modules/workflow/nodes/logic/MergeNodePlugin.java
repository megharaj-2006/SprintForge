package org.SprintForge.modules.workflow.nodes.logic;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MergeNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("merge.logic")
                .displayName("Merge Logic")
                .category("LOGIC")
                .icon("git-merge")
                .description("Joins multiple execution branches into a single stream.")
                .branches(List.of(NodeBranchPort.builder().label("SUCCESS").description("On branch merge completion").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        return NodeExecutionResult.success(Map.of("mergedAt", System.currentTimeMillis()));
    }
}
