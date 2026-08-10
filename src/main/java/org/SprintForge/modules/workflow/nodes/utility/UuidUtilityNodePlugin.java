package org.SprintForge.modules.workflow.nodes.utility;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class UuidUtilityNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("uuid.utility")
                .displayName("UUID Generator")
                .category("UTILITY")
                .icon("key")
                .description("Generates a new random UUID v4 string.")
                .outputs(List.of(
                        NodeOutputPort.builder().name("uuid").type("STRING").description("Generated UUID v4 string").build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").description("UUID generated").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String uuid = UUID.randomUUID().toString();
        return NodeExecutionResult.success(Map.of("uuid", uuid));
    }
}
