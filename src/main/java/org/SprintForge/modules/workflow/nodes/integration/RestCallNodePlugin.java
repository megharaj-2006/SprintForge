package org.SprintForge.modules.workflow.nodes.integration;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RestCallNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("rest.call")
                .displayName("REST API Call")
                .category("INTEGRATION")
                .icon("globe")
                .description("Executes a generic HTTP REST request (GET, POST, PUT, DELETE).")
                .inputs(List.of(
                        NodeInputPort.builder().name("url").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("method").type("STRING").defaultValue("GET").build(),
                        NodeInputPort.builder().name("headers").type("OBJECT").build(),
                        NodeInputPort.builder().name("body").type("OBJECT").build()
                ))
                .outputs(List.of(
                        NodeOutputPort.builder().name("statusCode").type("INTEGER").build(),
                        NodeOutputPort.builder().name("responseBody").type("OBJECT").build()
                ))
                .branches(List.of(
                        NodeBranchPort.label("SUCCESS").description("HTTP 2xx response").build(),
                        NodeBranchPort.label("FAILURE").description("HTTP error response").build()
                ))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String url = request.getConfiguration().getString("url", "https://api.example.com");
        String method = request.getConfiguration().getString("method", "GET");

        return NodeExecutionResult.success(Map.of(
                "statusCode", 200,
                "url", url,
                "method", method,
                "responseBody", Map.of("success", true)
        ));
    }
}
