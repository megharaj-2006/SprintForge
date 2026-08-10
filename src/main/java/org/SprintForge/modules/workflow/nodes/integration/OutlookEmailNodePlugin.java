package org.SprintForge.modules.workflow.nodes.integration;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OutlookEmailNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("outlook.email")
                .displayName("Outlook Send Email")
                .category("INTEGRATION")
                .icon("mail")
                .description("Sends an email via Outlook Graph API (Integration placeholder).")
                .inputs(List.of(
                        NodeInputPort.builder().name("to").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("subject").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("body").type("STRING").required(true).build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String to = request.getConfiguration().getString("to", "user@domain.com");
        return NodeExecutionResult.success(Map.of("status", "VALIDATED", "to", to, "sent", true));
    }
}
