package org.SprintForge.modules.ai.provider;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class AIProviderRegistry {

    private final Map<String, AIProviderClient> registry = new ConcurrentHashMap<>();

    public AIProviderRegistry(List<AIProviderClient> clients) {
        if (clients != null) {
            for (AIProviderClient client : clients) {
                register(client);
            }
        }
    }

    public void register(AIProviderClient client) {
        log.info("Registering AIProviderClient for key: {}", client.getProviderKey());
        registry.put(client.getProviderKey().toLowerCase(), client);
    }

    public AIProviderClient getClient(String providerKey) {
        if (providerKey == null) {
            return registry.get("openai");
        }
        AIProviderClient client = registry.get(providerKey.toLowerCase());
        if (client == null) {
            throw new ResourceNotFoundException("AIProviderClient not registered for providerKey: " + providerKey);
        }
        return client;
    }

    public Optional<AIProviderClient> findClient(String providerKey) {
        return Optional.ofNullable(registry.get(providerKey != null ? providerKey.toLowerCase() : "openai"));
    }

    public List<AIProviderClient> getAllClients() {
        return List.copyOf(registry.values());
    }
}
