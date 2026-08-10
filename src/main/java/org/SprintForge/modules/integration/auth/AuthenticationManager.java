package org.SprintForge.modules.integration.auth;

import org.SprintForge.modules.integration.entity.enums.CredentialType;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationManager {

    public Map<String, String> generateAuthHeaders(CredentialType type, String decryptedCredential) {
        Map<String, String> headers = new HashMap<>();
        if (decryptedCredential == null || decryptedCredential.isBlank()) {
            return headers;
        }

        switch (type) {
            case BEARER_TOKEN -> headers.put("Authorization", "Bearer " + decryptedCredential);
            case API_KEY -> headers.put("X-API-Key", decryptedCredential);
            case BASIC -> headers.put("Authorization", "Basic " + Base64.getEncoder().encodeToString(decryptedCredential.getBytes()));
            case OAUTH2 -> headers.put("Authorization", "Bearer " + decryptedCredential);
            default -> headers.put("Authorization", decryptedCredential);
        }
        return headers;
    }
}
