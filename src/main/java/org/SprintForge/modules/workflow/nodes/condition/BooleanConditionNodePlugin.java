package org.SprintForge.modules.workflow.nodes.condition;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BooleanConditionNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("boolean.condition")
                .displayName("Boolean Condition")
                .category("CONDITION")
                .icon("toggle-left")
                .description("Evaluates a boolean value or expression.")
                .inputs(List.of(
                        NodeInputPort.builder().name("value").type("BOOLEAN").required(true).build()
                ))
                .branches(List.of(
                        NodeBranchPort.label("TRUE").description("Boolean is true").build(),
                        NodeBranchPort.label("FALSE").description("Boolean is false").build()
                ))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        boolean val = request.getConfiguration().getBoolean("value", false);
        String branch = val ? "TRUE" : "FALSE";
        return NodeExecutionResult.success(Map.of("result", val), branch);
    }
}
