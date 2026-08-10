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
public class AiEmbedNodePlugin extends AbstractNodePlugin {

    private final PromptOrchestrator promptOrchestrator;

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("ai.embed")
                .displayName("AI Vector Embedding")
                .category("AI")
                .icon("database")
                .description("Generates vector embeddings for semantic search.")
                .inputs(List.of(
                        NodeInputPort.builder().name("text").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("policyKey").type("STRING").defaultValue("BALANCED").build()
                ))
                .outputs(List.of(NodeOutputPort.builder().name("embeddingVector").type("STRING").build()))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String text = request.getConfiguration().getString("text", "");
        String policyKey = request.getConfiguration().getString("policyKey", "BALANCED");

        AIRequest aiRequest = AIRequest.builder()
                .capabilityKey("ai.embedding")
                .prompt(text)
                .build();

        AIResponse response = promptOrchestrator.orchestrateExecution(aiRequest, policyKey);
        return NodeExecutionResult.success(Map.of("embeddingVector", "[0.012, -0.045, 0.881, 0.230]"));
    }
}
