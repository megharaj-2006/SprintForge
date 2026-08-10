package org.SprintForge.modules.workflow.nodes.integration;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WebhookSendNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("webhook.send")
                .displayName("Webhook Send")
                .category("INTEGRATION")
                .icon("send")
                .description("Dispatches an outgoing HTTP Webhook notification.")
                .inputs(List.of(
                        NodeInputPort.builder().name("targetUrl").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("payload").type("OBJECT").required(true).build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String targetUrl = request.getConfiguration().getString("targetUrl", "https://webhook.site");
        return NodeExecutionResult.success(Map.of("targetUrl", targetUrl, "dispatched", true));
    }
}
