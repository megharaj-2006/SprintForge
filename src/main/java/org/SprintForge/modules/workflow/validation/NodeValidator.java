package org.SprintForge.modules.workflow.validation;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workflow.dto.WorkflowValidationResponse;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.SprintForge.modules.workflow.executor.NodeExecutorRegistry;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NodeValidator {

    private final NodeExecutorRegistry executorRegistry;

    public void validateNode(WorkflowNode node, WorkflowValidationResponse response) {
        if (node.getDefinition() == null) {
            response.addError("MISSING_NODE_DEFINITION", "Node '" + node.getName() + "' is missing a NodeDefinition", String.valueOf(node.getId()));
            return;
        }

        String nodeKey = node.getDefinition().getNodeKey();
        if (!executorRegistry.isSupported(nodeKey)) {
            response.addError("UNSUPPORTED_NODE_KEY", "No NodeExecutor found for nodeKey '" + nodeKey + "' on node '" + node.getName() + "'", String.valueOf(node.getId()));
        }
    }
}
