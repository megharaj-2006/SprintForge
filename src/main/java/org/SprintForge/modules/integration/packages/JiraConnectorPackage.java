package org.SprintForge.modules.integration.packages;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.integration.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JiraConnectorPackage implements IntegrationConnectorPackage {

    @Override
    public String providerKey() {
        return "jira";
    }

    @Override
    public List<ConnectorOperation> operations() {
        return List.of(
                new ConnectorOperation() {
                    @Override public String getOperationKey() { return "CREATE_ISSUE"; }
                    @Override
                    public OperationDescriptor getDescriptor() {
                        return OperationDescriptor.builder().operationKey("CREATE_ISSUE").displayName("Create Jira Issue").build();
                    }
                    @Override public boolean validate(OperationContext context) { return true; }
                    @Override
                    public OperationResult execute(OperationContext context) {
                        return OperationResult.success(Map.of("issueKey", "PROJ-101", "id", "10001"));
                    }
                }
        );
    }

    @Override
    public List<ConnectorDescriptor> descriptors() {
        return List.of(
                ConnectorDescriptor.builder()
                        .connectorKey("jira")
                        .displayName("Atlassian Jira Connector Package")
                        .category("PROJECT_MANAGEMENT")
                        .version("1.0.0")
                        .supportedCapabilities(List.of("CREATE_ISSUE", "UPDATE_ISSUE", "TRANSITION_ISSUE", "SEARCH_ISSUES", "ADD_COMMENT"))
                        .authenticationTypes(List.of("OAUTH2", "PAT", "BASIC"))
                        .build()
        );
    }

    @Override
    public ConnectorClient client() {
        return new ConnectorClient() {
            @Override public String getProviderKey() { return "jira"; }
            @Override public ConnectorDescriptor getDescriptor() { return descriptors().get(0); }
            @Override public ConnectorResponse execute(ConnectorRequest request) { return ConnectorResponse.success(Map.of("jiraCloud", true)); }
            @Override public boolean isHealthy() { return true; }
        };
    }
}
