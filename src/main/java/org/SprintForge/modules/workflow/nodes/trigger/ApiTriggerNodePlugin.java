package org.SprintForge.modules.workflow.nodes.trigger;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ApiTriggerNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("api.trigger")
                .displayName("API Trigger")
                .category("TRIGGER")
                .icon("api")
                .description("Triggers workflow execution via REST API payload.")
                .outputs(List.of(
                        NodeOutputPort.builder().name("requestPayload").type("OBJECT").description("Incoming API payload").build()
                ))
                .branches(List.of(NodeBranchPort.builder().label("SUCCESS").description("On REST API invocation").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        Map<String, Object> input = request.getInputData();
        return NodeExecutionResult.success(Map.of("requestPayload", input != null ? input : Map.of()));
    }
}
