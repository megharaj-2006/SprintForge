package org.SprintForge.modules.workflow.nodes.ai;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.service.SummarizationService;
import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AiSummarizeNodePlugin extends AbstractNodePlugin {

    private final SummarizationService summarizationService;

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("ai.summarize")
                .displayName("AI Summarize")
                .category("AI")
                .icon("file-text")
                .description("Summarizes text content using AI models.")
                .inputs(List.of(
                        NodeInputPort.builder().name("content").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("policyKey").type("STRING").defaultValue("BALANCED").build()
                ))
                .outputs(List.of(NodeOutputPort.builder().name("summary").type("STRING").build()))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String content = request.getConfiguration().getString("content", "");
        String policyKey = request.getConfiguration().getString("policyKey", "BALANCED");

        String summary = summarizationService.summarize("GENERAL", content, policyKey);
        return NodeExecutionResult.success(Map.of("summary", summary));
    }
}
