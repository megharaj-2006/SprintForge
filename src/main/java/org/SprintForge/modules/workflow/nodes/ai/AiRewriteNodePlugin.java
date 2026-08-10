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
public class AiRewriteNodePlugin extends AbstractNodePlugin {

    private final PromptOrchestrator promptOrchestrator;

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("ai.rewrite")
                .displayName("AI Rewrite & Improve")
                .category("AI")
                .icon("edit")
                .description("Rewrites or improves text tone, grammar, and style.")
                .inputs(List.of(
                        NodeInputPort.builder().name("text").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("targetTone").type("STRING").defaultValue("PROFESSIONAL").build(),
                        NodeInputPort.builder().name("policyKey").type("STRING").defaultValue("BALANCED").build()
                ))
                .outputs(List.of(NodeOutputPort.builder().name("rewrittenText").type("STRING").build()))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String text = request.getConfiguration().getString("text", "");
        String tone = request.getConfiguration().getString("targetTone", "PROFESSIONAL");
        String policyKey = request.getConfiguration().getString("policyKey", "BALANCED");

        AIRequest aiRequest = AIRequest.builder()
                .capabilityKey("ai.generate")
                .prompt("Rewrite the following text with a " + tone + " tone:\n" + text)
                .build();

        AIResponse response = promptOrchestrator.orchestrateExecution(aiRequest, policyKey);
        return NodeExecutionResult.success(Map.of("rewrittenText", response.getContent()));
    }
}
