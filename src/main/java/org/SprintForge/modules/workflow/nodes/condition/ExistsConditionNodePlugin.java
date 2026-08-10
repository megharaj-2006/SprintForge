package org.SprintForge.modules.workflow.nodes.condition;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ExistsConditionNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("exists.condition")
                .displayName("Exists Condition")
                .category("CONDITION")
                .icon("eye")
                .description("Checks whether a variable exists in the execution context.")
                .inputs(List.of(
                        NodeInputPort.builder().name("variableName").type("STRING").required(true).build()
                ))
                .branches(List.of(
                        NodeBranchPort.label("TRUE").description("Variable exists and is non-null").build(),
                        NodeBranchPort.label("FALSE").description("Variable does not exist").build()
                ))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String varName = request.getConfiguration().getString("variableName", "");
        Object val = request.getContext().resolveVariable(varName);

        boolean exists = val != null;
        String branch = exists ? "TRUE" : "FALSE";

        return NodeExecutionResult.success(Map.of("exists", exists), branch);
    }
}
