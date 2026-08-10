package org.SprintForge.modules.workflow;

import org.SprintForge.modules.workflow.entity.WorkflowConnection;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.SprintForge.modules.workflow.graph.CycleDetector;
import org.SprintForge.modules.workflow.graph.WorkflowGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CycleDetectorTest {

    @Test
    void testAcyclicGraphHasNoCycle() {
        CycleDetector detector = new CycleDetector();
        WorkflowGraph graph = new WorkflowGraph();

        WorkflowNode n1 = createNode(1L, "Start");
        WorkflowNode n2 = createNode(2L, "Process");
        WorkflowNode n3 = createNode(3L, "End");

        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);

        graph.addConnection(createConnection(10L, n1, n2));
        graph.addConnection(createConnection(11L, n2, n3));

        assertFalse(detector.hasCycle(graph));
    }

    @Test
    void testCyclicGraphHasCycle() {
        CycleDetector detector = new CycleDetector();
        WorkflowGraph graph = new WorkflowGraph();

        WorkflowNode n1 = createNode(1L, "Start");
        WorkflowNode n2 = createNode(2L, "Loop");

        graph.addNode(n1);
        graph.addNode(n2);

        graph.addConnection(createConnection(10L, n1, n2));
        graph.addConnection(createConnection(11L, n2, n1));

        assertTrue(detector.hasCycle(graph));
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
