package org.SprintForge.modules.integration;

import org.SprintForge.modules.integration.auth.AuthenticationManager;
import org.SprintForge.modules.integration.entity.enums.CredentialType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthenticationManagerTest {

    @Test
    void testBearerTokenHeaderGeneration() {
        AuthenticationManager authManager = new AuthenticationManager();
        Map<String, String> headers = authManager.generateAuthHeaders(CredentialType.BEARER_TOKEN, "mySecretToken123");

        assertTrue(headers.containsKey("Authorization"));
        assertEquals("Bearer mySecretToken123", headers.get("Authorization"));
    }

    @Test
    void testApiKeyHeaderGeneration() {
        AuthenticationManager authManager = new AuthenticationManager();
        Map<String, String> headers = authManager.generateAuthHeaders(CredentialType.API_KEY, "key-456");

        assertTrue(headers.containsKey("X-API-Key"));
        assertEquals("key-456", headers.get("X-API-Key"));
    }
}
