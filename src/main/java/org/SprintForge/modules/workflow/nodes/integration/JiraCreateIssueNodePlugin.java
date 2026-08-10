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
public class JiraCreateIssueNodePlugin extends AbstractNodePlugin {

    private final ConnectorOperationRegistry connectorOperationRegistry;

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("jira.createIssue")
                .displayName("Jira Create Issue")
                .category("INTEGRATION")
                .icon("check-square")
                .description("Creates a ticket/issue in Jira Cloud.")
                .inputs(List.of(
                        NodeInputPort.builder().name("projectKey").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("summary").type("STRING").required(true).build()
                ))
                .outputs(List.of(NodeOutputPort.builder().name("issueKey").type("STRING").build()))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String projectKey = request.getConfiguration().getString("projectKey", "PROJ");
        String summary = request.getConfiguration().getString("summary", "Jira Summary");

        OperationContext context = OperationContext.builder()
                .providerKey("jira")
                .operationKey("CREATE_ISSUE")
                .inputs(Map.of("projectKey", projectKey, "summary", summary))
                .build();

        OperationResult result = connectorOperationRegistry.executeOperation(context);
        return NodeExecutionResult.success(result.getOutputs());
    }
}
