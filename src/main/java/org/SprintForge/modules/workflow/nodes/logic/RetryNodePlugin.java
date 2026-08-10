package org.SprintForge.modules.workflow.nodes.logic;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RetryNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("retry.logic")
                .displayName("Retry Logic")
                .category("LOGIC")
                .icon("refresh-cw")
                .description("Delegates node retry handling to the Platform Retry service.")
                .inputs(List.of(
                        NodeInputPort.builder().name("maxRetries").type("INTEGER").defaultValue(3).build(),
                        NodeInputPort.builder().name("retryIntervalSeconds").type("INTEGER").defaultValue(5).build()
                ))
                .capabilities(NodeCapabilities.builder().retryable(true).build())
                .branches(List.of(NodeBranchPort.builder().label("SUCCESS").description("On retry success").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        int maxRetries = request.getConfiguration().getInt("maxRetries", 3);
        return NodeExecutionResult.success(Map.of("maxRetries", maxRetries, "status", "RETRY_DELEGATED"));
    }
}
