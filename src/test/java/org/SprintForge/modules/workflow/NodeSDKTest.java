package org.SprintForge.modules.workflow;

import org.SprintForge.modules.workflow.sdk.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NodeSDKTest {

    @Test
    void testTypedPortsNodeDescriptor() {
        NodeDescriptor descriptor = NodeDescriptor.builder()
                .nodeKey("test.node")
                .displayName("Test Node")
                .category("TEST")
                .inputs(List.of(NodeInputPort.builder().name("in1").type("STRING").required(true).build()))
                .outputs(List.of(NodeOutputPort.builder().name("out1").type("BOOLEAN").build()))
                .branches(List.of(NodeBranchPort.builder().label("SUCCESS").build()))
                .capabilities(NodeCapabilities.builder().async(true).retryable(true).build())
                .build();

        assertEquals("test.node", descriptor.getNodeKey());
        assertEquals("TEST", descriptor.getCategory());
        assertEquals(1, descriptor.getInputs().size());
        assertEquals(1, descriptor.getOutputs().size());
        assertTrue(descriptor.getCapabilities().isAsync());
    }

    @Test
    void testNodeConfigurationBinding() {
        NodeConfiguration config = new NodeConfiguration(Map.of("key1", "value1", "count", 42, "flag", true));
        assertEquals("value1", config.getString("key1", "default"));
        assertEquals(42, config.getInt("count", 0));
        assertTrue(config.getBoolean("flag", false));
    }
}
