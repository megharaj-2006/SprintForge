package org.SprintForge.modules.workflow.nodes.logic;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BranchNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("branch.logic")
                .displayName("Branch Logic")
                .category("LOGIC")
                .icon("git-branch")
                .description("Routes execution flow along TRUE, FALSE, or CUSTOM branch paths.")
                .inputs(List.of(
                        NodeInputPort.builder().name("conditionResult").type("BOOLEAN").description("Evaluated condition value").required(true).build()
                ))
                .branches(List.of(
                        NodeBranchPort.builder().label("TRUE").description("Executed when condition is true").build(),
                        NodeBranchPort.builder().label("FALSE").description("Executed when condition is false").build(),
                        NodeBranchPort.builder().label("DEFAULT").description("Fallback execution path").build()
                ))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        boolean result = request.getConfiguration().getBoolean("conditionResult", true);
        String targetBranch = result ? "TRUE" : "FALSE";
        return NodeExecutionResult.success(Map.of("evaluatedBranch", targetBranch), targetBranch);
    }
}
