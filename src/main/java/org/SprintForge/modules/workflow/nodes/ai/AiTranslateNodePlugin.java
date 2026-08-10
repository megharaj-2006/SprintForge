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
public class AiTranslateNodePlugin extends AbstractNodePlugin {

    private final PromptOrchestrator promptOrchestrator;

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("ai.translate")
                .displayName("AI Translate")
                .category("AI")
                .icon("globe")
                .description("Translates text between languages using AI models.")
                .inputs(List.of(
                        NodeInputPort.builder().name("text").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("targetLanguage").type("STRING").defaultValue("es").build(),
                        NodeInputPort.builder().name("policyKey").type("STRING").defaultValue("BALANCED").build()
                ))
                .outputs(List.of(NodeOutputPort.builder().name("translatedText").type("STRING").build()))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String text = request.getConfiguration().getString("text", "");
        String targetLang = request.getConfiguration().getString("targetLanguage", "es");
        String policyKey = request.getConfiguration().getString("policyKey", "BALANCED");

        AIRequest aiRequest = AIRequest.builder()
                .capabilityKey("ai.translate")
                .prompt("Translate the following text to " + targetLang + ":\n" + text)
                .build();

        AIResponse response = promptOrchestrator.orchestrateExecution(aiRequest, policyKey);
        return NodeExecutionResult.success(Map.of("translatedText", response.getContent()));
    }
}
