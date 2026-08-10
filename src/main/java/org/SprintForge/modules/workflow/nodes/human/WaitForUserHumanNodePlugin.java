package org.SprintForge.modules.workflow.nodes.human;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WaitForUserHumanNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("wait.human")
                .displayName("Wait For User")
                .category("HUMAN")
                .icon("pause-circle")
                .description("Pauses workflow execution until explicit user resume event occurs.")
                .branches(List.of(NodeBranchPort.label("DEFAULT").description("Resumed path").build()))
                .capabilities(NodeCapabilities.builder().humanInteraction(true).async(true).build())
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        return NodeExecutionResult.success(Map.of("resumedAt", System.currentTimeMillis(), "status", "RESUMED"));
    }
}
