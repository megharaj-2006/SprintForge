package org.SprintForge.modules.workflow.nodes.trigger;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WebhookTriggerNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("webhook.trigger")
                .displayName("Webhook Trigger")
                .category("TRIGGER")
                .icon("webhook")
                .description("Triggers workflow from incoming HTTP Webhook POST requests.")
                .outputs(List.of(
                        NodeOutputPort.builder().name("headers").type("OBJECT").description("HTTP Headers").build(),
                        NodeOutputPort.builder().name("body").type("OBJECT").description("HTTP Request Body").build()
                ))
                .branches(List.of(NodeBranchPort.builder().label("SUCCESS").description("On HTTP webhook POST").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        return NodeExecutionResult.success(Map.of(
                "headers", Map.of("Content-Type", "application/json"),
                "body", request.getInputData() != null ? request.getInputData() : Map.of()
        ));
    }
}
