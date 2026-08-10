package org.SprintForge.modules.workflow.nodes.logic;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LoopNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("loop.logic")
                .displayName("Loop Logic")
                .category("LOGIC")
                .icon("repeat")
                .description("Iterates over a count limit, collection items, or until a condition is met.")
                .inputs(List.of(
                        NodeInputPort.builder().name("loopType").type("STRING").description("COUNT, COLLECTION, CONDITION").defaultValue("COUNT").build(),
                        NodeInputPort.builder().name("maxIterations").type("INTEGER").description("Max iteration count").defaultValue(10).build()
                ))
                .outputs(List.of(
                        NodeOutputPort.builder().name("currentIndex").type("INTEGER").description("Current iteration index").build()
                ))
                .branches(List.of(
                        NodeBranchPort.builder().label("TRUE").description("Loop iteration path").build(),
                        NodeBranchPort.builder().label("FALSE").description("Loop exit path").build()
                ))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        int maxIterations = request.getConfiguration().getInt("maxIterations", 10);
        int currentIndex = request.getConfiguration().getInt("currentIndex", 0);

        boolean continueLoop = currentIndex < maxIterations;
        String branch = continueLoop ? "TRUE" : "FALSE";

        return NodeExecutionResult.success(Map.of(
                "currentIndex", currentIndex,
                "nextIndex", currentIndex + 1,
                "completed", !continueLoop
        ), branch);
    }
}
