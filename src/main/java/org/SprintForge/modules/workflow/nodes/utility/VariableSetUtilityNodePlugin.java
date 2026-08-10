package org.SprintForge.modules.workflow.nodes.utility;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class VariableSetUtilityNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("variable.set")
                .displayName("Variable Setter")
                .category("UTILITY")
                .icon("database")
                .description("Sets a variable into the runtime execution context.")
                .inputs(List.of(
                        NodeInputPort.builder().name("name").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("value").type("OBJECT").required(true).build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").description("Variable set").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String name = request.getConfiguration().getString("name", "var");
        Object val = request.getConfiguration().get("value");

        return NodeExecutionResult.success(Map.of("name", name, "value", val != null ? val : ""));
    }
}
