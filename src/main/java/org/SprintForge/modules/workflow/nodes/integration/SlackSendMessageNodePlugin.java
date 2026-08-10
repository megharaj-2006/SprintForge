package org.SprintForge.modules.workflow.nodes.integration;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SlackSendMessageNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("slack.sendMessage")
                .displayName("Slack Send Message")
                .category("INTEGRATION")
                .icon("message-square")
                .description("Posts a message to a Slack channel (Integration placeholder).")
                .inputs(List.of(
                        NodeInputPort.builder().name("channel").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("message").type("STRING").required(true).build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String channel = request.getConfiguration().getString("channel", "#general");
        return NodeExecutionResult.success(Map.of("status", "VALIDATED", "channel", channel));
    }
}
