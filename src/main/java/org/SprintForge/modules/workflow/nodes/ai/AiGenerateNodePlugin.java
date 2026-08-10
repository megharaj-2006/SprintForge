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
public class AiGenerateNodePlugin extends AbstractNodePlugin {

    private final PromptOrchestrator promptOrchestrator;

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("ai.generate")
                .displayName("AI Generate")
                .category("AI")
                .icon("cpu")
                .description("Generates text using AI LLM models.")
                .inputs(List.of(
                        NodeInputPort.builder().name("prompt").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("policyKey").type("STRING").defaultValue("BALANCED").build()
                ))
                .outputs(List.of(NodeOutputPort.builder().name("generatedText").type("STRING").build()))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String promptText = request.getConfiguration().getString("prompt", "Hello AI");
        String policyKey = request.getConfiguration().getString("policyKey", "BALANCED");

        AIRequest aiRequest = AIRequest.builder()
                .capabilityKey("ai.generate")
                .prompt(promptText)
                .build();

        AIResponse response = promptOrchestrator.orchestrateExecution(aiRequest, policyKey);
        return NodeExecutionResult.success(Map.of("generatedText", response.getContent()));
    }
}
