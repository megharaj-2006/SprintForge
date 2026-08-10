package org.SprintForge.modules.workflow.nodes.utility;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class VariableGetUtilityNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("variable.get")
                .displayName("Variable Reader")
                .category("UTILITY")
                .icon("search")
                .description("Reads a variable value from the execution context.")
                .inputs(List.of(
                        NodeInputPort.builder().name("name").type("STRING").required(true).build()
                ))
                .outputs(List.of(
                        NodeOutputPort.builder().name("value").type("OBJECT").description("Resolved variable value").build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").description("Variable read").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String name = request.getConfiguration().getString("name", "var");
        Object val = request.getContext().resolveVariable(name);

        return NodeExecutionResult.success(Map.of("value", val != null ? val : ""));
    }
}
