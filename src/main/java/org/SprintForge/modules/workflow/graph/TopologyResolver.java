package org.SprintForge.modules.workflow.graph;

import org.SprintForge.modules.workflow.entity.WorkflowConnection;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TopologyResolver {

    public List<WorkflowNode> getTopologicalOrder(WorkflowGraph graph) {
        Map<Long, Integer> inDegree = new HashMap<>();
        for (WorkflowNode node : graph.getAllNodes()) {
            inDegree.put(node.getId(), graph.getIncoming(node.getId()).size());
        }

        Queue<WorkflowNode> queue = new LinkedList<>(graph.getStartNodes());
        List<WorkflowNode> sorted = new ArrayList<>();

        while (!queue.isEmpty()) {
            WorkflowNode node = queue.poll();
            sorted.add(node);

            for (WorkflowConnection conn : graph.getOutgoing(node.getId())) {
                Long targetId = conn.getTargetNode().getId();
                int currentIn = inDegree.getOrDefault(targetId, 0) - 1;
                inDegree.put(targetId, currentIn);
                if (currentIn == 0) {
                    sorted.add(graph.getNode(targetId));
                    queue.add(graph.getNode(targetId));
                }
            }
        }

        // Deduplicate maintaining order
        Set<Long> seen = new HashSet<>();
        List<WorkflowNode> result = new ArrayList<>();
        for (WorkflowNode n : sorted) {
            if (seen.add(n.getId())) {
                result.add(n);
            }
        }
        return result;
    }
}
