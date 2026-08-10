package org.SprintForge.modules.integration.registry;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.integration.sdk.ConnectorClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ConnectorRegistry {

    private final Map<String, ConnectorClient> registry = new ConcurrentHashMap<>();

    public ConnectorRegistry(List<ConnectorClient> clients) {
        if (clients != null) {
            for (ConnectorClient client : clients) {
                register(client);
            }
        }
    }

    public void register(ConnectorClient client) {
        log.info("Registering ConnectorClient for providerKey: {}", client.getProviderKey());
        registry.put(client.getProviderKey().toLowerCase(), client);
    }

    public ConnectorClient getClient(String providerKey) {
        if (providerKey == null) {
            return registry.get("rest.generic");
        }
        ConnectorClient client = registry.get(providerKey.toLowerCase());
        if (client == null) {
            throw new ResourceNotFoundException("ConnectorClient not registered for providerKey: " + providerKey);
        }
        return client;
    }

    public Optional<ConnectorClient> findClient(String providerKey) {
        return Optional.ofNullable(registry.get(providerKey != null ? providerKey.toLowerCase() : "rest.generic"));
    }

    public List<ConnectorClient> getAllClients() {
        return List.copyOf(registry.values());
    }
}
