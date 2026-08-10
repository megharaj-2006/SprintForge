package org.SprintForge.modules.workflow;

import org.SprintForge.modules.workflow.dto.WorkflowValidationResponse;
import org.SprintForge.modules.workflow.entity.WorkflowConnection;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.SprintForge.modules.workflow.graph.CycleDetector;
import org.SprintForge.modules.workflow.graph.GraphValidator;
import org.SprintForge.modules.workflow.graph.WorkflowGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphValidatorTest {

    @Test
    void testValidGraphPassesValidation() {
        CycleDetector detector = new CycleDetector();
        GraphValidator validator = new GraphValidator(detector);

        WorkflowGraph graph = new WorkflowGraph();
        WorkflowNode start = createNode(1L, "Start");
        WorkflowNode end = createNode(2L, "End");

        graph.addNode(start);
        graph.addNode(end);
        graph.addConnection(createConnection(10L, start, end));

        WorkflowValidationResponse response = validator.validate(graph);
        assertTrue(response.isValid());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    void testNoStartNodeFailsValidation() {
        CycleDetector detector = new CycleDetector();
        GraphValidator validator = new GraphValidator(detector);

        WorkflowGraph graph = new WorkflowGraph();
        WorkflowNode node1 = createNode(1L, "A");
        WorkflowNode node2 = createNode(2L, "B");
        graph.addNode(node1);
        graph.addNode(node2);
        // Connect bi-directionally so no node has in-degree 0
        graph.addConnection(createConnection(10L, node1, node2));
        graph.addConnection(createConnection(11L, node2, node1));

        WorkflowValidationResponse response = validator.validate(graph);
        assertFalse(response.isValid());
    }

    private WorkflowNode createNode(Long id, String name) {
        WorkflowNode node = new WorkflowNode();
        node.setId(id);
        node.setName(name);
        return node;
    }

    private WorkflowConnection createConnection(Long id, WorkflowNode source, WorkflowNode target) {
        WorkflowConnection conn = new WorkflowConnection();
        conn.setId(id);
        conn.setSourceNode(source);
        conn.setTargetNode(target);
        return conn;
    }
}
