package org.SprintForge.modules.workflow.graph;

import org.SprintForge.modules.workflow.entity.WorkflowConnection;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.SprintForge.modules.workflow.entity.WorkflowVersion;
import org.springframework.stereotype.Component;

@Component
public class GraphBuilder {

    public WorkflowGraph buildGraph(WorkflowVersion version) {
        WorkflowGraph graph = new WorkflowGraph();

        if (version.getNodes() != null) {
            for (WorkflowNode node : version.getNodes()) {
                if (Boolean.TRUE.equals(node.getEnabled())) {
                    graph.addNode(node);
                }
            }
        }

        if (version.getConnections() != null) {
            for (WorkflowConnection connection : version.getConnections()) {
                if (graph.getNode(connection.getSourceNode().getId()) != null &&
                    graph.getNode(connection.getTargetNode().getId()) != null) {
                    graph.addConnection(connection);
                }
            }
        }

        return graph;
    }
}
