package org.SprintForge.modules.workflow.simulation;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BreakpointManager {

    private final Map<String, Set<Long>> breakpoints = new ConcurrentHashMap<>();

    public void addBreakpoint(String simulationId, Long nodeId) {
        breakpoints.computeIfAbsent(simulationId, k -> ConcurrentHashMap.newKeySet()).add(nodeId);
    }

    public void removeBreakpoint(String simulationId, Long nodeId) {
        if (breakpoints.containsKey(simulationId)) {
            breakpoints.get(simulationId).remove(nodeId);
        }
    }

    public boolean isBreakpointHit(String simulationId, Long nodeId) {
        return breakpoints.containsKey(simulationId) && breakpoints.get(simulationId).contains(nodeId);
    }
}
