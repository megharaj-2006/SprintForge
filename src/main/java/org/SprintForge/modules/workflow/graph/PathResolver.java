package org.SprintForge.modules.workflow.graph;

import org.SprintForge.modules.workflow.entity.WorkflowConnection;
import org.SprintForge.modules.workflow.entity.enums.BranchLabel;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PathResolver {

    public List<WorkflowConnection> resolveNextConnections(WorkflowGraph graph, Long currentNodeId, String executionResultStatus, String outputData) {
        List<WorkflowConnection> outgoing = graph.getOutgoing(currentNodeId);
        if (outgoing.isEmpty()) {
            return List.of();
        }

        // Sort by priority descending
        List<WorkflowConnection> sorted = outgoing.stream()
                .sorted(Comparator.comparingInt(WorkflowConnection::getPriority).reversed())
                .collect(Collectors.toList());

        // Match based on BranchLabel
        List<WorkflowConnection> matched = sorted.stream()
                .filter(conn -> isBranchMatched(conn, executionResultStatus))
                .collect(Collectors.toList());

        if (!matched.isEmpty()) {
            return matched;
        }

        // Fallback to DEFAULT branch
        return sorted.stream()
                .filter(conn -> conn.getBranchLabel() == BranchLabel.DEFAULT)
                .collect(Collectors.toList());
    }

    private boolean isBranchMatched(WorkflowConnection connection, String executionResultStatus) {
        BranchLabel label = connection.getBranchLabel();
        if (label == BranchLabel.DEFAULT) {
            return false;
        }
        if (label == BranchLabel.SUCCESS && "SUCCESS".equalsIgnoreCase(executionResultStatus)) {
            return true;
        }
        if (label == BranchLabel.FAILURE && "FAILURE".equalsIgnoreCase(executionResultStatus)) {
            return true;
        }
        if (label == BranchLabel.TRUE && "TRUE".equalsIgnoreCase(executionResultStatus)) {
            return true;
        }
        if (label == BranchLabel.FALSE && "FALSE".equalsIgnoreCase(executionResultStatus)) {
            return true;
        }
        return false;
    }
}
