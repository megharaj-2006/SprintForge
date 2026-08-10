package org.SprintForge.modules.workflow.nodes.integration;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GithubCreateReleaseNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("github.createRelease")
                .displayName("GitHub Create Release")
                .category("INTEGRATION")
                .icon("github")
                .description("Creates a new release tag in GitHub (Integration placeholder).")
                .inputs(List.of(
                        NodeInputPort.builder().name("repository").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("tagName").type("STRING").required(true).build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String tag = request.getConfiguration().getString("tagName", "v1.0.0");
        return NodeExecutionResult.success(Map.of("status", "VALIDATED", "tagName", tag));
    }
}
