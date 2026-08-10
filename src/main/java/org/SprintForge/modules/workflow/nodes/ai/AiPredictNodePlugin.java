package org.SprintForge.modules.workflow.nodes.ai;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.orchestration.PromptOrchestrator;
import org.SprintForge.modules.ai.provider.AIRequest;
import org.SprintForge.modules.ai.provider.AIResponse;
import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AiPredictNodePlugin extends AbstractNodePlugin {

    private final PromptOrchestrator promptOrchestrator;

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("ai.predict")
                .displayName("AI Predict")
                .category("AI")
                .icon("trending-up")
                .description("Generates predictive insights or forecasting using AI models.")
                .inputs(List.of(
                        NodeInputPort.builder().name("data").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("policyKey").type("STRING").defaultValue("BALANCED").build()
                ))
                .outputs(List.of(NodeOutputPort.builder().name("prediction").type("STRING").build()))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String data = request.getConfiguration().getString("data", "");
        String policyKey = request.getConfiguration().getString("policyKey", "BALANCED");

        AIRequest aiRequest = AIRequest.builder()
                .capabilityKey("ai.prediction")
                .prompt("Predict outcome for input data:\n" + data)
                .build();

        AIResponse response = promptOrchestrator.orchestrateExecution(aiRequest, policyKey);
        return NodeExecutionResult.success(Map.of("prediction", response.getContent()));
    }
}
