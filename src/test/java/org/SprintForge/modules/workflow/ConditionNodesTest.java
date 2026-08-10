package org.SprintForge.modules.workflow;

import org.SprintForge.modules.workflow.nodes.condition.EqualsConditionNodePlugin;
import org.SprintForge.modules.workflow.nodes.condition.NumberConditionNodePlugin;
import org.SprintForge.modules.workflow.sdk.NodeConfiguration;
import org.SprintForge.modules.workflow.sdk.NodeContext;
import org.SprintForge.modules.workflow.sdk.NodeExecutionRequest;
import org.SprintForge.modules.workflow.sdk.NodeExecutionResult;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConditionNodesTest {

    @Test
    void testEqualsConditionTrue() {
        EqualsConditionNodePlugin plugin = new EqualsConditionNodePlugin();
        NodeExecutionRequest request = NodeExecutionRequest.builder()
                .context(NodeContext.builder().executionId("1").build())
                .configuration(new NodeConfiguration(Map.of("value1", "hello", "value2", "hello")))
                .build();

        NodeExecutionResult result = plugin.executePlugin(request);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("TRUE", result.getNextBranchLabel());
        assertTrue((Boolean) result.getOutputs().get("result"));
    }

    @Test
    void testNumberConditionGreaterThan() {
        NumberConditionNodePlugin plugin = new NumberConditionNodePlugin();
        NodeExecutionRequest request = NodeExecutionRequest.builder()
                .context(NodeContext.builder().executionId("1").build())
                .configuration(new NodeConfiguration(Map.of("num1", 100, "operator", ">", "num2", 50)))
                .build();

        NodeExecutionResult result = plugin.executePlugin(request);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("TRUE", result.getNextBranchLabel());
    }
}
