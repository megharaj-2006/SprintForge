package org.SprintForge.modules.workflow.nodes.integration;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TeamsSendMessageNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("teams.sendMessage")
                .displayName("Microsoft Teams Send Message")
                .category("INTEGRATION")
                .icon("message-circle")
                .description("Posts a message to MS Teams channel (Integration placeholder).")
                .inputs(List.of(
                        NodeInputPort.builder().name("webhookUrl").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("message").type("STRING").required(true).build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        return NodeExecutionResult.success(Map.of("status", "VALIDATED", "sent", true));
    }
}
