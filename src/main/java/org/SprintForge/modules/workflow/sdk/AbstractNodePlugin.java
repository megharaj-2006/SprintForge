package org.SprintForge.modules.workflow.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.SprintForge.modules.workflow.executor.ExecutionContext;
import org.SprintForge.modules.workflow.executor.NodeExecutor;
import org.SprintForge.modules.workflow.executor.NodeExecutorResult;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNodePlugin implements NodeExecutor {

    private static final ObjectMapper mapper = new ObjectMapper();

    public abstract NodeDescriptor getDescriptor();

    public abstract NodeExecutionResult executePlugin(NodeExecutionRequest request);

    @Override
    public String getNodeKey() {
        return getDescriptor().getNodeKey();
    }

    @Override
    @SneakyThrows
    public NodeExecutorResult execute(ExecutionContext context, WorkflowNode node) {
        long startTime = System.currentTimeMillis();

        Map<String, Object> rawConfig = new HashMap<>();
        if (node.getDefinition() != null && node.getDefinition().getConfigurationSchema() != null) {
            try {
                rawConfig = mapper.readValue(node.getDefinition().getConfigurationSchema(), Map.class);
            } catch (Exception ignored) {}
        }

        NodeContext sdkContext = NodeContext.builder()
                .executionId(context.getExecutionId())
                .correlationId(context.getCorrelationId())
                .nodeId(String.valueOf(node.getId()))
                .nodeName(node.getName())
                .globalVariables(context.getGlobalVariables())
                .executionVariables(context.getExecutionVariables())
                .nodeVariables(context.getNodeVariables())
                .previousOutputs(context.getNodeOutputs())
                .metadata(context.getMetadata())
                .build();

        NodeExecutionRequest sdkRequest = NodeExecutionRequest.builder()
                .context(sdkContext)
                .configuration(new NodeConfiguration(rawConfig))
                .inputData(context.getVariables())
                .build();

        NodeExecutionResult sdkResult = executePlugin(sdkRequest);
        sdkResult.setExecutionTimeMillis(System.currentTimeMillis() - startTime);

        if ("SUCCESS".equalsIgnoreCase(sdkResult.getStatus())) {
            return NodeExecutorResult.success(sdkResult.getOutputs());
        } else {
            return NodeExecutorResult.failure(sdkResult.getErrorMessage());
        }
    }
}
