package org.SprintForge.modules.integration;

import org.SprintForge.modules.integration.provider.MockRestConnectorClient;
import org.SprintForge.modules.integration.registry.ConnectorRegistry;
import org.SprintForge.modules.integration.registry.OperationRegistry;
import org.SprintForge.modules.integration.sdk.ConnectorRequest;
import org.SprintForge.modules.integration.sdk.ConnectorResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperationRegistryTest {

    @Test
    void testOperationRegistryDispatch() {
        MockRestConnectorClient client = new MockRestConnectorClient();
        ConnectorRegistry connectorRegistry = new ConnectorRegistry(List.of(client));
        OperationRegistry operationRegistry = new OperationRegistry(connectorRegistry);

        ConnectorRequest request = ConnectorRequest.builder()
                .providerKey("rest.generic")
                .capabilityKey("SEND_MESSAGE")
                .build();

        ConnectorResponse response = operationRegistry.executeOperation(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals("SUCCESS", response.getStatus());
        assertTrue(response.getLatencyMs() >= 0);
    }
}
