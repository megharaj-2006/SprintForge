package org.SprintForge.modules.integration.registry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.integration.sdk.ConnectorClient;
import org.SprintForge.modules.integration.sdk.ConnectorRequest;
import org.SprintForge.modules.integration.sdk.ConnectorResponse;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OperationRegistry {

    private final ConnectorRegistry connectorRegistry;

    public ConnectorResponse executeOperation(ConnectorRequest request) {
        long startTime = System.currentTimeMillis();
        String providerKey = request.getProviderKey() != null ? request.getProviderKey() : "rest.generic";

        log.info("OperationRegistry dispatching capability [{}] to connector [{}]", request.getCapabilityKey(), providerKey);
        ConnectorClient client = connectorRegistry.getClient(providerKey);

        ConnectorResponse response = client.execute(request);
        response.setLatencyMs(System.currentTimeMillis() - startTime);
        return response;
    }
}
