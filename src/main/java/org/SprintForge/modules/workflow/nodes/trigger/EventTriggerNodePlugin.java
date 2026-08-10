package org.SprintForge.modules.workflow.nodes.trigger;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class EventTriggerNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("event.trigger")
                .displayName("Event Trigger")
                .category("TRIGGER")
                .icon("zap")
                .description("Triggers workflow on Platform Events (Task, Project, Workspace, System).")
                .inputs(List.of(
                        NodeInputPort.builder().name("eventType").type("STRING").description("Event type (TASK_CREATED, PROJECT_UPDATED)").required(true).build()
                ))
                .outputs(List.of(
                        NodeOutputPort.builder().name("eventType").type("STRING").description("Captured event type").build(),
                        NodeOutputPort.builder().name("eventPayload").type("OBJECT").description("Event details payload").build()
                ))
                .branches(List.of(NodeBranchPort.builder().label("SUCCESS").description("On event emission").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String eventType = request.getConfiguration().getString("eventType", "SYSTEM_EVENT");
        return NodeExecutionResult.success(Map.of(
                "eventType", eventType,
                "eventPayload", request.getInputData() != null ? request.getInputData() : Map.of()
        ));
    }
}
