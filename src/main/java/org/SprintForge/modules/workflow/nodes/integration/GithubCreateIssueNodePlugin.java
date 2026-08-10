package org.SprintForge.modules.workflow.nodes.integration;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.integration.registry.ConnectorOperationRegistry;
import org.SprintForge.modules.integration.sdk.OperationContext;
import org.SprintForge.modules.integration.sdk.OperationResult;
import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GithubCreateIssueNodePlugin extends AbstractNodePlugin {

    private final ConnectorOperationRegistry connectorOperationRegistry;

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("github.createIssue")
                .displayName("GitHub Create Issue")
                .category("INTEGRATION")
                .icon("github")
                .description("Creates an issue in GitHub repository.")
                .inputs(List.of(
                        NodeInputPort.builder().name("repository").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("title").type("STRING").required(true).build()
                ))
                .outputs(List.of(NodeOutputPort.builder().name("issueNumber").type("INTEGER").build()))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String repo = request.getConfiguration().getString("repository", "org/repo");
        String title = request.getConfiguration().getString("title", "Issue Title");

        OperationContext context = OperationContext.builder()
                .providerKey("github")
                .operationKey("CREATE_ISSUE")
                .inputs(Map.of("repository", repo, "title", title))
                .build();

        OperationResult result = connectorOperationRegistry.executeOperation(context);
        return NodeExecutionResult.success(result.getOutputs());
    }
}
