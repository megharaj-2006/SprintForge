package org.SprintForge.modules.integration;

import org.SprintForge.modules.integration.auth.IntegrationAuthenticationManager;
import org.SprintForge.modules.integration.entity.enums.CredentialType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthenticationManagerTest {

    @Test
    void testBearerTokenHeader() {
        IntegrationAuthenticationManager manager = new IntegrationAuthenticationManager();
        Map<String, String> headers = manager.generateAuthHeaders(CredentialType.BEARER_TOKEN, "secret-token");

        assertEquals("Bearer secret-token", headers.get("Authorization"));
    }

    @Test
    void testApiKeyHeader() {
        IntegrationAuthenticationManager manager = new IntegrationAuthenticationManager();
        Map<String, String> headers = manager.generateAuthHeaders(CredentialType.API_KEY, "my-key");

        assertEquals("my-key", headers.get("X-API-Key"));
    }
}
