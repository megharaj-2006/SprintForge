package org.SprintForge.modules.workflow.nodes.condition;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class EqualsConditionNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("equals.condition")
                .displayName("Equals Condition")
                .category("CONDITION")
                .icon("check-square")
                .description("Compares two values for equality.")
                .inputs(List.of(
                        NodeInputPort.builder().name("value1").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("value2").type("STRING").required(true).build()
                ))
                .outputs(List.of(
                        NodeOutputPort.builder().name("result").type("BOOLEAN").description("Comparison result").build()
                ))
                .branches(List.of(
                        NodeBranchPort.label("TRUE").description("Values are equal").build(),
                        NodeBranchPort.label("FALSE").description("Values are not equal").build()
                ))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        Object v1 = request.getConfiguration().get("value1");
        Object v2 = request.getConfiguration().get("value2");

        boolean match = Objects.equals(v1, v2);
        String branch = match ? "TRUE" : "FALSE";

        return NodeExecutionResult.success(Map.of("result", match), branch);
    }
}
