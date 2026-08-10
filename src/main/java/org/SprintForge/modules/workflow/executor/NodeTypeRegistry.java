package org.SprintForge.modules.workflow.executor;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workflow.sdk.AbstractNodePlugin;
import org.SprintForge.modules.workflow.sdk.NodeDescriptor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class NodeTypeRegistry {

    private final Map<String, NodeExecutor> registry = new ConcurrentHashMap<>();

    public NodeTypeRegistry(List<NodeExecutor> executors) {
        if (executors != null) {
            for (NodeExecutor executor : executors) {
                register(executor);
            }
        }
    }

    public void register(NodeExecutor executor) {
        log.info("Registering NodeExecutor for nodeKey: {}", executor.getNodeKey());
        registry.put(executor.getNodeKey(), executor);
    }

    public Optional<NodeExecutor> getExecutor(String nodeKey) {
        return Optional.ofNullable(registry.get(nodeKey));
    }

    public boolean hasExecutor(String nodeKey) {
        return registry.containsKey(nodeKey);
    }

    public List<NodeDescriptor> getAllDescriptors() {
        return registry.values().stream()
                .map(executor -> {
                    if (executor instanceof AbstractNodePlugin plugin) {
                        return plugin.getDescriptor();
                    }
                    return NodeDescriptor.builder()
                            .nodeKey(executor.getNodeKey())
                            .displayName(executor.getNodeKey())
                            .category("GENERIC")
                            .description("Generic node executor plugin")
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<NodeDescriptor> getDescriptorsByCategory(String category) {
        return getAllDescriptors().stream()
                .filter(d -> d.getCategory() != null && d.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public Optional<NodeDescriptor> getDescriptorByKey(String nodeKey) {
        return getAllDescriptors().stream()
                .filter(d -> d.getNodeKey() != null && d.getNodeKey().equalsIgnoreCase(nodeKey))
                .findFirst();
    }

    public List<NodeDescriptor> searchDescriptors(String query) {
        if (query == null || query.isBlank()) {
            return getAllDescriptors();
        }
        String q = query.toLowerCase();
        return getAllDescriptors().stream()
                .filter(d -> (d.getNodeKey() != null && d.getNodeKey().toLowerCase().contains(q)) ||
                             (d.getDisplayName() != null && d.getDisplayName().toLowerCase().contains(q)) ||
                             (d.getDescription() != null && d.getDescription().toLowerCase().contains(q)))
                .collect(Collectors.toList());
    }
}
