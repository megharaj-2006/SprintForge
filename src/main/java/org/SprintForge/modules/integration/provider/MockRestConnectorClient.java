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
public class MockRestConnectorClient implements ConnectorClient {

    @Override
    public String getProviderKey() {
        return "rest.generic";
    }

    @Override
    public ConnectorDescriptor getDescriptor() {
        return ConnectorDescriptor.builder()
                .connectorKey("rest.generic")
                .displayName("Generic REST Connector")
                .category("REST")
                .version("1.0.0")
                .description("Base HTTP REST integration client")
                .supportedCapabilities(List.of("CREATE_ISSUE", "SEND_MESSAGE", "UPLOAD_FILE", "SEARCH", "UPDATE", "DELETE"))
                .authenticationTypes(List.of("API_KEY", "BEARER_TOKEN", "BASIC"))
                .build();
    }

    @Override
    public ConnectorResponse execute(ConnectorRequest request) {
        log.info("[MOCK REST CONNECTOR] Executing capability {} endpoint: {}", request.getCapabilityKey(), request.getEndpointUrl());
        return ConnectorResponse.builder()
                .statusCode(200)
                .status("SUCCESS")
                .payload(Map.of(
                        "capability", request.getCapabilityKey(),
                        "endpoint", request.getEndpointUrl() != null ? request.getEndpointUrl() : "https://api.external.com",
                        "processed", true
                ))
                .metadata(Map.of("providerKey", getProviderKey(), "simulated", true))
                .build();
    }

    @Override
    public boolean isHealthy() {
        return true;
    }
}
