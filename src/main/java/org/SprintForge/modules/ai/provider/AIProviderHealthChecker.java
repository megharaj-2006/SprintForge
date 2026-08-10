package org.SprintForge.modules.ai.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AIProviderHealthChecker {

    private final AIProviderRegistry providerRegistry;

    public Map<String, Boolean> checkAllHealth() {
        Map<String, Boolean> healthMap = new HashMap<>();
        for (AIProviderClient client : providerRegistry.getAllClients()) {
            healthMap.put(client.getProviderKey(), client.isHealthy());
        }
        return healthMap;
    }
}
