package org.SprintForge.modules.integration.packages;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.integration.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class WebhookConnectorPackage implements IntegrationConnectorPackage {

    @Override public String providerKey() { return "webhook"; }

    @Override
    public List<ConnectorOperation> operations() {
        return List.of(
                new ConnectorOperation() {
                    @Override public String getOperationKey() { return "SEND_WEBHOOK"; }
                    @Override public OperationDescriptor getDescriptor() { return OperationDescriptor.builder().operationKey("SEND_WEBHOOK").displayName("Send Webhook Notification").build(); }
                    @Override public boolean validate(OperationContext context) { return true; }
                    @Override public OperationResult execute(OperationContext context) { return OperationResult.success(Map.of("dispatched", true)); }
                }
        );
    }

    @Override
    public List<ConnectorDescriptor> descriptors() {
        return List.of(ConnectorDescriptor.builder().connectorKey("webhook").displayName("Generic Webhook Package").category("CORE").version("1.0.0").supportedCapabilities(List.of("SEND_WEBHOOK", "RECEIVE_WEBHOOK")).authenticationTypes(List.of("BEARER_TOKEN", "CUSTOM_SIGNATURE")).build());
    }

    @Override
    public ConnectorClient client() {
        return new ConnectorClient() {
            @Override public String getProviderKey() { return "webhook"; }
            @Override public ConnectorDescriptor getDescriptor() { return descriptors().get(0); }
            @Override public ConnectorResponse execute(ConnectorRequest request) { return ConnectorResponse.success(Map.of("webhook", true)); }
            @Override public boolean isHealthy() { return true; }
        };
    }
}
