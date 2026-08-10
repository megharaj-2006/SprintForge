package org.SprintForge.modules.workflow.graph;

import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExecutionPlanner {

    public List<WorkflowNode> createExecutionPlan(WorkflowGraph graph) {
        TopologyResolver topologyResolver = new TopologyResolver();
        return topologyResolver.getTopologicalOrder(graph);
    }
}
