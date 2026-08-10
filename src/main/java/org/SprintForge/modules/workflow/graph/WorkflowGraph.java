package org.SprintForge.modules.workflow.graph;

import lombok.Getter;
import org.SprintForge.modules.workflow.entity.WorkflowConnection;
import org.SprintForge.modules.workflow.entity.WorkflowNode;

import java.util.*;

@Getter
public class WorkflowGraph {
    private final Map<Long, WorkflowNode> nodes = new LinkedHashMap<>();
    private final Map<Long, List<WorkflowConnection>> outgoingConnections = new HashMap<>();
    private final Map<Long, List<WorkflowConnection>> incomingConnections = new HashMap<>();

    public void addNode(WorkflowNode node) {
        nodes.put(node.getId(), node);
        outgoingConnections.putIfAbsent(node.getId(), new ArrayList<>());
        incomingConnections.putIfAbsent(node.getId(), new ArrayList<>());
    }

    public void addConnection(WorkflowConnection connection) {
        Long sourceId = connection.getSourceNode().getId();
        Long targetId = connection.getTargetNode().getId();

        outgoingConnections.computeIfAbsent(sourceId, k -> new ArrayList<>()).add(connection);
        incomingConnections.computeIfAbsent(targetId, k -> new ArrayList<>()).add(connection);
    }

    public WorkflowNode getNode(Long nodeId) {
        return nodes.get(nodeId);
    }

    public List<WorkflowNode> getAllNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<WorkflowConnection> getOutgoing(Long nodeId) {
        return outgoingConnections.getOrDefault(nodeId, Collections.emptyList());
    }

    public List<WorkflowConnection> getIncoming(Long nodeId) {
        return incomingConnections.getOrDefault(nodeId, Collections.emptyList());
    }

    public List<WorkflowNode> getStartNodes() {
        List<WorkflowNode> startNodes = new ArrayList<>();
        for (WorkflowNode node : nodes.values()) {
            if (getIncoming(node.getId()).isEmpty()) {
                startNodes.add(node);
            }
        }
        return startNodes;
    }

    public List<WorkflowNode> getEndNodes() {
        List<WorkflowNode> endNodes = new ArrayList<>();
        for (WorkflowNode node : nodes.values()) {
            if (getOutgoing(node.getId()).isEmpty()) {
                endNodes.add(node);
            }
        }
        return endNodes;
    }
}
