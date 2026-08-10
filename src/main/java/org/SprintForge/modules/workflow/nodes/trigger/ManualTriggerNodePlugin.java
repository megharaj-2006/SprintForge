package org.SprintForge.modules.workflow.nodes.trigger;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ManualTriggerNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("manual.trigger")
                .displayName("Manual Trigger")
                .category("TRIGGER")
                .icon("play-circle")
                .description("Starts the workflow manually from the UI or API.")
                .outputs(List.of(
                        NodeOutputPort.builder().name("triggeredAt").type("LONG").description("Timestamp when trigger occurred").build(),
                        NodeOutputPort.builder().name("triggeredBy").type("STRING").description("User or system ID").build()
                ))
                .branches(List.of(NodeBranchPort.builder().label("SUCCESS").description("On manual trigger initiation").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        return NodeExecutionResult.success(Map.of(
                "triggeredAt", System.currentTimeMillis(),
                "triggeredBy", request.getContext().getCurrentUserId() != null ? request.getContext().getCurrentUserId() : "USER"
        ));
    }
}
