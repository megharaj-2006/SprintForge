package org.SprintForge.modules.workflow.nodes.condition;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DateConditionNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("date.condition")
                .displayName("Date Condition")
                .category("CONDITION")
                .icon("calendar")
                .description("Compares timestamps or dates (BEFORE, AFTER, EQUALS).")
                .inputs(List.of(
                        NodeInputPort.builder().name("date1Millis").type("LONG").required(true).build(),
                        NodeInputPort.builder().name("operator").type("STRING").required(true).defaultValue("BEFORE").build(),
                        NodeInputPort.builder().name("date2Millis").type("LONG").required(true).build()
                ))
                .branches(List.of(
                        NodeBranchPort.label("TRUE").description("Date comparison holds true").build(),
                        NodeBranchPort.label("FALSE").description("Date comparison holds false").build()
                ))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        long d1 = request.getConfiguration().getInt("date1Millis", 0);
        long d2 = request.getConfiguration().getInt("date2Millis", 0);
        String op = request.getConfiguration().getString("operator", "BEFORE");

        boolean result = switch (op.toUpperCase()) {
            case "AFTER" -> d1 > d2;
            case "BEFORE" -> d1 < d2;
            default -> d1 == d2;
        };

        String branch = result ? "TRUE" : "FALSE";
        return NodeExecutionResult.success(Map.of("result", result), branch);
    }
}
