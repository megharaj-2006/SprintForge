package org.SprintForge.modules.workflow;

import org.SprintForge.modules.workflow.executor.NodeExecutor;
import org.SprintForge.modules.workflow.executor.NodeTypeRegistry;
import org.SprintForge.modules.workflow.executor.impl.ManualTriggerExecutor;
import org.SprintForge.modules.workflow.executor.impl.PassThroughExecutor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NodeTypeRegistryTest {

    @Test
    void testRegistryFindsRegisteredExecutors() {
        ManualTriggerExecutor manualTrigger = new ManualTriggerExecutor();
        PassThroughExecutor passThrough = new PassThroughExecutor();

        NodeTypeRegistry registry = new NodeTypeRegistry(List.of(manualTrigger, passThrough));

        Optional<NodeExecutor> executor = registry.getExecutor("manual.trigger");
        assertTrue(executor.isPresent());
        assertTrue(executor.get() instanceof ManualTriggerExecutor);

        Optional<NodeExecutor> passThroughExec = registry.getExecutor("passthrough.logic");
        assertTrue(passThroughExec.isPresent());
    }
}
