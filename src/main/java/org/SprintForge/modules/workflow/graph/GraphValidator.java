package org.SprintForge.modules.workflow.graph;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workflow.dto.WorkflowValidationResponse;
import org.SprintForge.modules.workflow.entity.WorkflowConnection;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class GraphValidator {

    private final CycleDetector cycleDetector;

    public WorkflowValidationResponse validate(WorkflowGraph graph) {
        WorkflowValidationResponse response = new WorkflowValidationResponse();
        response.setValid(true);

        List<WorkflowNode> startNodes = graph.getStartNodes();
        if (startNodes.isEmpty()) {
            response.addError("NO_START_NODE", "Workflow must contain exactly one Start Node", null);
        } else if (startNodes.size() > 1) {
            response.addError("MULTIPLE_START_NODES", "Workflow contains multiple Start Nodes (" + startNodes.size() + ")", null);
        }

        List<WorkflowNode> endNodes = graph.getEndNodes();
        if (endNodes.isEmpty()) {
            response.addError("NO_END_NODE", "Workflow must contain at least one End Node", null);
        }

        if (cycleDetector.hasCycle(graph)) {
            response.addError("CYCLE_DETECTED", "Workflow graph contains illegal cycles", null);
        }

        // Detect unreachable nodes from start nodes
        if (!startNodes.isEmpty()) {
            Set<Long> reachable = new HashSet<>();
            for (WorkflowNode startNode : startNodes) {
                dfsReachable(startNode.getId(), graph, reachable);
            }

            for (WorkflowNode node : graph.getAllNodes()) {
                if (!reachable.contains(node.getId())) {
                    response.addError("UNREACHABLE_NODE", "Node '" + node.getName() + "' (ID: " + node.getId() + ") is unreachable", String.valueOf(node.getId()));
                }
            }
        }

        return response;
    }

    private void dfsReachable(Long nodeId, WorkflowGraph graph, Set<Long> reachable) {
        if (!reachable.add(nodeId)) {
            return;
        }
        for (WorkflowConnection conn : graph.getOutgoing(nodeId)) {
            dfsReachable(conn.getTargetNode().getId(), graph, reachable);
        }
    }
}
