package org.SprintForge.modules.integration;

import org.SprintForge.modules.integration.provider.MockRestConnectorClient;
import org.SprintForge.modules.integration.sdk.ConnectorClient;
import org.SprintForge.modules.integration.sdk.ConnectorRequest;
import org.SprintForge.modules.integration.sdk.ConnectorResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectorSDKTest {

    @Test
    void testMockRestConnectorClientExecution() {
        ConnectorClient client = new MockRestConnectorClient();

        ConnectorRequest request = ConnectorRequest.builder()
                .providerKey("rest.generic")
                .capabilityKey("CREATE_ISSUE")
                .endpointUrl("https://api.github.com/repos/org/repo/issues")
                .build();

        ConnectorResponse response = client.execute(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals("SUCCESS", response.getStatus());
        assertTrue(response.getPayload().containsKey("capability"));
        assertEquals("CREATE_ISSUE", response.getPayload().get("capability"));
    }
}
