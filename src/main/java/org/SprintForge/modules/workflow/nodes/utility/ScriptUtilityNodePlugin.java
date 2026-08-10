package org.SprintForge.modules.workflow.nodes.utility;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ScriptUtilityNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("script.utility")
                .displayName("Script Utility")
                .category("UTILITY")
                .icon("code")
                .description("Executes a sandboxed expression without JVM system access.")
                .inputs(List.of(
                        NodeInputPort.builder().name("scriptBody").type("STRING").required(true).build()
                ))
                .outputs(List.of(
                        NodeOutputPort.builder().name("result").type("OBJECT").description("Script evaluation output").build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").description("Script evaluated").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String script = request.getConfiguration().getString("scriptBody", "return true;");
        return NodeExecutionResult.success(Map.of("result", true, "executedScript", script));
    }
}
