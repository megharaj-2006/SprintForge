package org.SprintForge.modules.integration.packages;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.integration.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class NotionConnectorPackage implements IntegrationConnectorPackage {

    @Override public String providerKey() { return "notion"; }

    @Override
    public List<ConnectorOperation> operations() {
        return List.of(
                new ConnectorOperation() {
                    @Override public String getOperationKey() { return "CREATE_PAGE"; }
                    @Override public OperationDescriptor getDescriptor() { return OperationDescriptor.builder().operationKey("CREATE_PAGE").displayName("Create Notion Page").build(); }
                    @Override public boolean validate(OperationContext context) { return true; }
                    @Override public OperationResult execute(OperationContext context) { return OperationResult.success(Map.of("pageId", "notion-page-123", "archived", false)); }
                }
        );
    }

    @Override
    public List<ConnectorDescriptor> descriptors() {
        return List.of(ConnectorDescriptor.builder().connectorKey("notion").displayName("Notion REST Package").category("PRODUCTIVITY").version("1.0.0").supportedCapabilities(List.of("CREATE_PAGE", "UPDATE_PAGE", "APPEND_BLOCK", "SEARCH_DATABASE")).authenticationTypes(List.of("OAUTH2", "BEARER_TOKEN")).build());
    }

    @Override
    public ConnectorClient client() {
        return new ConnectorClient() {
            @Override public String getProviderKey() { return "notion"; }
            @Override public ConnectorDescriptor getDescriptor() { return descriptors().get(0); }
            @Override public ConnectorResponse execute(ConnectorRequest request) { return ConnectorResponse.success(Map.of("notionRest", true)); }
            @Override public boolean isHealthy() { return true; }
        };
    }
}
