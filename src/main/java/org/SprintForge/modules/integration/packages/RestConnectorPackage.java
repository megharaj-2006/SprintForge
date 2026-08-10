package org.SprintForge.modules.integration.packages;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.integration.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RestConnectorPackage implements IntegrationConnectorPackage {

    @Override public String providerKey() { return "rest"; }

    @Override
    public List<ConnectorOperation> operations() {
        return List.of(
                new ConnectorOperation() {
                    @Override public String getOperationKey() { return "POST"; }
                    @Override public OperationDescriptor getDescriptor() { return OperationDescriptor.builder().operationKey("POST").displayName("REST HTTP POST").build(); }
                    @Override public boolean validate(OperationContext context) { return true; }
                    @Override public OperationResult execute(OperationContext context) { return OperationResult.success(Map.of("statusCode", 200, "status", "SUCCESS")); }
                }
        );
    }

    @Override
    public List<ConnectorDescriptor> descriptors() {
        return List.of(ConnectorDescriptor.builder().connectorKey("rest").displayName("Generic REST Package").category("CORE").version("1.0.0").supportedCapabilities(List.of("GET", "POST", "PUT", "PATCH", "DELETE")).authenticationTypes(List.of("API_KEY", "BEARER_TOKEN", "BASIC")).build());
    }

    @Override
    public ConnectorClient client() {
        return new ConnectorClient() {
            @Override public String getProviderKey() { return "rest"; }
            @Override public ConnectorDescriptor getDescriptor() { return descriptors().get(0); }
            @Override public ConnectorResponse execute(ConnectorRequest request) { return ConnectorResponse.success(Map.of("rest", true)); }
            @Override public boolean isHealthy() { return true; }
        };
    }
}
