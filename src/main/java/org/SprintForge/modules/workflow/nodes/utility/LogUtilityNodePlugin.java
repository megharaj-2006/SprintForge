package org.SprintForge.modules.workflow.nodes.utility;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LogUtilityNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("log.utility")
                .displayName("Logger Utility")
                .category("UTILITY")
                .icon("file-text")
                .description("Logs message to workflow execution logs and audit platform.")
                .inputs(List.of(
                        NodeInputPort.builder().name("message").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("level").type("STRING").defaultValue("INFO").build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").description("Message logged").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String msg = request.getConfiguration().getString("message", "Workflow log entry");
        String level = request.getConfiguration().getString("level", "INFO");

        log.info("[WORKFLOW LOG - {}] {}", level, msg);
        return NodeExecutionResult.success(Map.of("loggedMessage", msg, "level", level));
    }
}
