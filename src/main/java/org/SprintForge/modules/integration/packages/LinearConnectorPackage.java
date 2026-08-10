package org.SprintForge.modules.integration.packages;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.integration.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LinearConnectorPackage implements IntegrationConnectorPackage {

    @Override public String providerKey() { return "linear"; }

    @Override
    public List<ConnectorOperation> operations() {
        return List.of(
                new ConnectorOperation() {
                    @Override public String getOperationKey() { return "CREATE_ISSUE"; }
                    @Override public OperationDescriptor getDescriptor() { return OperationDescriptor.builder().operationKey("CREATE_ISSUE").displayName("Create Linear Issue").build(); }
                    @Override public boolean validate(OperationContext context) { return true; }
                    @Override public OperationResult execute(OperationContext context) { return OperationResult.success(Map.of("issueId", "LIN-42", "team", "ENG")); }
                }
        );
    }

    @Override
    public List<ConnectorDescriptor> descriptors() {
        return List.of(ConnectorDescriptor.builder().connectorKey("linear").displayName("Linear GraphQL Package").category("PROJECT_MANAGEMENT").version("1.0.0").supportedCapabilities(List.of("CREATE_ISSUE", "UPDATE_ISSUE", "MOVE_ISSUE")).authenticationTypes(List.of("OAUTH2", "API_KEY")).build());
    }

    @Override
    public ConnectorClient client() {
        return new ConnectorClient() {
            @Override public String getProviderKey() { return "linear"; }
            @Override public ConnectorDescriptor getDescriptor() { return descriptors().get(0); }
            @Override public ConnectorResponse execute(ConnectorRequest request) { return ConnectorResponse.success(Map.of("linearGraphQL", true)); }
            @Override public boolean isHealthy() { return true; }
        };
    }
}
