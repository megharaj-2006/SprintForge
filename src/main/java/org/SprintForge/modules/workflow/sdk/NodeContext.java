package org.SprintForge.modules.workflow.sdk;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class NodeContext {
    private final String executionId;
    private final String correlationId;
    private final Long workspaceId;
    private final String currentUserId;
    private final String nodeId;
    private final String nodeName;
    private final Map<String, Object> globalVariables;
    private final Map<String, Object> executionVariables;
    private final Map<String, Map<String, Object>> nodeVariables;
    private final Map<String, Object> previousOutputs;
    private final Map<String, Object> metadata;

    public Object resolveVariable(String name) {
        if (nodeId != null && nodeVariables.containsKey(nodeId) && nodeVariables.get(nodeId).containsKey(name)) {
            return nodeVariables.get(nodeId).get(name);
        }
        if (executionVariables.containsKey(name)) {
            return executionVariables.get(name);
        }
        return globalVariables.get(name);
    }
}
