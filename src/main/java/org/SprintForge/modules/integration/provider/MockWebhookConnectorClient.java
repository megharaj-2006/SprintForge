package org.SprintForge.modules.integration.provider;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.integration.sdk.ConnectorClient;
import org.SprintForge.modules.integration.sdk.ConnectorDescriptor;
import org.SprintForge.modules.integration.sdk.ConnectorRequest;
import org.SprintForge.modules.integration.sdk.ConnectorResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MockWebhookConnectorClient implements ConnectorClient {

    @Override
    public String getProviderKey() {
        return "webhook.generic";
    }

    @Override
    public ConnectorDescriptor getDescriptor() {
        return ConnectorDescriptor.builder()
                .connectorKey("webhook.generic")
                .displayName("Generic Webhook Connector")
                .category("WEBHOOK")
                .version("1.0.0")
                .description("Base Webhook dispatcher and receiver")
                .supportedCapabilities(List.of("SEND_MESSAGE", "DISPATCH_WEBHOOK"))
                .authenticationTypes(List.of("BEARER_TOKEN", "CUSTOM_SIGNATURE"))
                .build();
    }

    @Override
    public ConnectorResponse execute(ConnectorRequest request) {
        log.info("[MOCK WEBHOOK CONNECTOR] Dispatching webhook capability {}", request.getCapabilityKey());
        return ConnectorResponse.builder()
                .statusCode(200)
                .status("SUCCESS")
                .payload(Map.of("dispatched", true, "capability", request.getCapabilityKey()))
                .metadata(Map.of("providerKey", getProviderKey(), "simulated", true))
                .build();
    }

    @Override
    public boolean isHealthy() {
        return true;
    }
}
