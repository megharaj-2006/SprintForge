package org.SprintForge.modules.workflow.nodes.logic;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DelayNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("delay.logic")
                .displayName("Delay Logic")
                .category("LOGIC")
                .icon("clock")
                .description("Pauses execution for a specified duration or until a target timestamp.")
                .inputs(List.of(
                        NodeInputPort.builder().name("durationValue").type("INTEGER").description("Number of time units").required(true).defaultValue(5).build(),
                        NodeInputPort.builder().name("unit").type("STRING").description("SECONDS, MINUTES, HOURS, DAYS").required(true).defaultValue("SECONDS").build()
                ))
                .outputs(List.of(
                        NodeOutputPort.builder().name("delayedMillis").type("LONG").description("Actual delay applied in milliseconds").build()
                ))
                .branches(List.of(NodeBranchPort.builder().label("SUCCESS").description("On delay completion").build()))
                .capabilities(NodeCapabilities.builder().async(true).retryable(true).build())
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        int durationValue = request.getConfiguration().getInt("durationValue", 5);
        String unit = request.getConfiguration().getString("unit", "SECONDS");

        long millis = switch (unit.toUpperCase()) {
            case "MINUTES" -> durationValue * 60L * 1000L;
            case "HOURS" -> durationValue * 3600L * 1000L;
            case "DAYS" -> durationValue * 86400L * 1000L;
            default -> durationValue * 1000L;
        };

        return NodeExecutionResult.success(Map.of("delayedMillis", millis, "unit", unit));
    }
}
