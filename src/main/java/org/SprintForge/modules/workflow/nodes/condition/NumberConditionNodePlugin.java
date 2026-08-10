package org.SprintForge.modules.workflow.nodes.condition;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class NumberConditionNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("number.condition")
                .displayName("Number Condition")
                .category("CONDITION")
                .icon("hash")
                .description("Performs numeric comparison (> , < , >= , <= , ==).")
                .inputs(List.of(
                        NodeInputPort.builder().name("num1").type("NUMBER").required(true).build(),
                        NodeInputPort.builder().name("operator").type("STRING").required(true).defaultValue("==").build(),
                        NodeInputPort.builder().name("num2").type("NUMBER").required(true).build()
                ))
                .branches(List.of(
                        NodeBranchPort.label("TRUE").description("Comparison evaluates to true").build(),
                        NodeBranchPort.label("FALSE").description("Comparison evaluates to false").build()
                ))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        double n1 = request.getConfiguration().getInt("num1", 0);
        double n2 = request.getConfiguration().getInt("num2", 0);
        String op = request.getConfiguration().getString("operator", "==");

        boolean result = switch (op) {
            case ">" -> n1 > n2;
            case "<" -> n1 < n2;
            case ">=" -> n1 >= n2;
            case "<=" -> n1 <= n2;
            default -> n1 == n2;
        };

        String branch = result ? "TRUE" : "FALSE";
        return NodeExecutionResult.success(Map.of("result", result), branch);
    }
}
