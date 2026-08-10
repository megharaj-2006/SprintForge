package org.SprintForge.modules.workflow.nodes.utility;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class RandomUtilityNodePlugin extends AbstractNodePlugin {

    private final Random random = new Random();

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("random.utility")
                .displayName("Random Utility")
                .category("UTILITY")
                .icon("shuffle")
                .description("Generates a random integer or double value.")
                .inputs(List.of(
                        NodeInputPort.builder().name("min").type("INTEGER").defaultValue(1).build(),
                        NodeInputPort.builder().name("max").type("INTEGER").defaultValue(100).build()
                ))
                .outputs(List.of(
                        NodeOutputPort.builder().name("randomValue").type("INTEGER").description("Generated random integer").build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").description("Random value generated").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        int min = request.getConfiguration().getInt("min", 1);
        int max = request.getConfiguration().getInt("max", 100);
        int val = random.nextInt((max - min) + 1) + min;

        return NodeExecutionResult.success(Map.of("randomValue", val));
    }
}
