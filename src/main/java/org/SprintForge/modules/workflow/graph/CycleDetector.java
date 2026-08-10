package org.SprintForge.modules.workflow.graph;

import org.SprintForge.modules.workflow.entity.WorkflowConnection;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CycleDetector {

    public boolean hasCycle(WorkflowGraph graph) {
        Set<Long> visited = new HashSet<>();
        Set<Long> recursionStack = new HashSet<>();

        for (WorkflowNode node : graph.getAllNodes()) {
            if (dfsHasCycle(node.getId(), graph, visited, recursionStack)) {
                return true;
            }
        }
        return false;
    }

    private boolean dfsHasCycle(Long nodeId, WorkflowGraph graph, Set<Long> visited, Set<Long> recursionStack) {
        if (recursionStack.contains(nodeId)) {
            return true;
        }
        if (visited.contains(nodeId)) {
            return false;
        }

        visited.add(nodeId);
        recursionStack.add(nodeId);

        for (WorkflowConnection conn : graph.getOutgoing(nodeId)) {
            if (dfsHasCycle(conn.getTargetNode().getId(), graph, visited, recursionStack)) {
                return true;
            }
        }

        recursionStack.remove(nodeId);
        return false;
    }
}
