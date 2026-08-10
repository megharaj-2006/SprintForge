package org.SprintForge.modules.workflow.executor;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class ExecutionContext {
    private final String executionId;
    private final String correlationId;
    
    private final Map<String, Object> globalVariables = new ConcurrentHashMap<>();
    private final Map<String, Object> executionVariables = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Object>> nodeVariables = new ConcurrentHashMap<>();
    private final Map<String, Object> nodeOutputs = new ConcurrentHashMap<>();
    private final Map<String, Object> metadata = new ConcurrentHashMap<>();

    public ExecutionContext(String executionId, String correlationId) {
        this.executionId = executionId;
        this.correlationId = correlationId;
    }

    public Object resolveVariable(String name, String nodeId) {
        // 1. Check Node Scope
        if (nodeId != null && nodeVariables.containsKey(nodeId)) {
            Map<String, Object> nodeScopeMap = nodeVariables.get(nodeId);
            if (nodeScopeMap.containsKey(name)) {
                return nodeScopeMap.get(name);
            }
        }

        // 2. Check Execution Scope
        if (executionVariables.containsKey(name)) {
            return executionVariables.get(name);
        }

        // 3. Fallback to Global Scope
        return globalVariables.get(name);
    }

    public Object getVariable(String name) {
        return resolveVariable(name, null);
    }

    public void setGlobalVariable(String name, Object value) {
        if (value != null) {
            globalVariables.put(name, value);
        } else {
            globalVariables.remove(name);
        }
    }

    public void setExecutionVariable(String name, Object value) {
        if (value != null) {
            executionVariables.put(name, value);
        } else {
            executionVariables.remove(name);
        }
    }

    public void setNodeVariable(String nodeId, String name, Object value) {
        nodeVariables.computeIfAbsent(nodeId, k -> new ConcurrentHashMap<>());
        if (value != null) {
            nodeVariables.get(nodeId).put(name, value);
        } else {
            nodeVariables.get(nodeId).remove(name);
        }
    }

    public void setNodeOutput(String nodeId, Object output) {
        if (output != null) {
            nodeOutputs.put(nodeId, output);
        }
    }

    public Map<String, Object> getVariables() {
        Map<String, Object> merged = new ConcurrentHashMap<>(globalVariables);
        merged.putAll(executionVariables);
        return merged;
    }
}
