package org.SprintForge.modules.workflow.executor;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NodeExecutorRegistry {

    private final NodeTypeRegistry nodeTypeRegistry;

    public NodeExecutor getExecutor(String nodeKey) {
        return nodeTypeRegistry.getExecutor(nodeKey)
                .orElseThrow(() -> new ResourceNotFoundException("NodeExecutor not found for nodeKey: " + nodeKey));
    }

    public boolean isSupported(String nodeKey) {
        return nodeTypeRegistry.hasExecutor(nodeKey);
    }
}
