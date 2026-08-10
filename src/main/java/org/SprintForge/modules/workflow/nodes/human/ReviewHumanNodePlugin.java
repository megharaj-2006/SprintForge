package org.SprintForge.modules.workflow.nodes.human;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ReviewHumanNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("review.human")
                .displayName("Human Review")
                .category("HUMAN")
                .icon("eye")
                .description("Prompts a human user to review data and add feedback comments.")
                .branches(List.of(NodeBranchPort.label("SUCCESS").description("Review completed").build()))
                .capabilities(NodeCapabilities.builder().humanInteraction(true).build())
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        return NodeExecutionResult.success(Map.of("reviewedAt", System.currentTimeMillis(), "status", "REVIEWED"));
    }
}
