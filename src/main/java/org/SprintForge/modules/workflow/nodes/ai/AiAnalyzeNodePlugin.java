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
public class AiAnalyzeNodePlugin extends AbstractNodePlugin {

    private final PromptOrchestrator promptOrchestrator;

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("ai.analyze")
                .displayName("AI Analyze")
                .category("AI")
                .icon("bar-chart-2")
                .description("Performs deep sentiment, risk, or pattern analysis using AI.")
                .inputs(List.of(
                        NodeInputPort.builder().name("content").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("policyKey").type("STRING").defaultValue("BALANCED").build()
                ))
                .outputs(List.of(NodeOutputPort.builder().name("analysisResult").type("STRING").build()))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String content = request.getConfiguration().getString("content", "");
        String policyKey = request.getConfiguration().getString("policyKey", "BALANCED");

        AIRequest aiRequest = AIRequest.builder()
                .capabilityKey("ai.reasoning")
                .prompt("Perform deep analysis on the following content:\n" + content)
                .build();

        AIResponse response = promptOrchestrator.orchestrateExecution(aiRequest, policyKey);
        return NodeExecutionResult.success(Map.of("analysisResult", response.getContent()));
    }
}
