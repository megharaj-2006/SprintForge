package org.SprintForge.modules.workflow.nodes.integration;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GoogleCalendarNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("google.calendar")
                .displayName("Google Calendar Event")
                .category("INTEGRATION")
                .icon("calendar")
                .description("Creates an event in Google Calendar (Integration placeholder).")
                .inputs(List.of(
                        NodeInputPort.builder().name("summary").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("startTime").type("STRING").required(true).build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String summary = request.getConfiguration().getString("summary", "Event Summary");
        return NodeExecutionResult.success(Map.of("status", "VALIDATED", "eventId", "gcal-12345", "summary", summary));
    }
}
