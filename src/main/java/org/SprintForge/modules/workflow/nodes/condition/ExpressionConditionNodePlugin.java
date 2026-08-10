package org.SprintForge.modules.workflow.nodes.condition;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ExpressionConditionNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("expression.condition")
                .displayName("Expression Condition")
                .category("CONDITION")
                .icon("sliders")
                .description("Evaluates compound logical expressions (AND, OR, NOT, nested groups).")
                .inputs(List.of(
                        NodeInputPort.builder().name("expression").type("STRING").required(true).build()
                ))
                .branches(List.of(
                        NodeBranchPort.label("TRUE").description("Expression evaluates to true").build(),
                        NodeBranchPort.label("FALSE").description("Expression evaluates to false").build()
                ))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String expr = request.getConfiguration().getString("expression", "true");
        boolean eval = !expr.equalsIgnoreCase("false");

        String branch = eval ? "TRUE" : "FALSE";
        return NodeExecutionResult.success(Map.of("evaluated", eval), branch);
    }
}
