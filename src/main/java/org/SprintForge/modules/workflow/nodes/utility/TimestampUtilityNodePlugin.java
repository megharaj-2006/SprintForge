package org.SprintForge.modules.workflow.nodes.utility;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
public class TimestampUtilityNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("timestamp.utility")
                .displayName("Timestamp Generator")
                .category("UTILITY")
                .icon("clock")
                .description("Outputs current system epoch millisecond timestamp and ISO-8601 string.")
                .outputs(List.of(
                        NodeOutputPort.builder().name("epochMillis").type("LONG").description("Epoch milliseconds").build(),
                        NodeOutputPort.builder().name("isoString").type("STRING").description("ISO-8601 timestamp string").build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").description("Timestamp generated").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        long now = System.currentTimeMillis();
        String iso = Instant.ofEpochMilli(now).toString();

        return NodeExecutionResult.success(Map.of("epochMillis", now, "isoString", iso));
    }
}
