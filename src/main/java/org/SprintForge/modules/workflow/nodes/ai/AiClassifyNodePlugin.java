package org.SprintForge.modules.workflow.nodes.ai;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.service.ClassificationService;
import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AiClassifyNodePlugin extends AbstractNodePlugin {

    private final ClassificationService classificationService;

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("ai.classify")
                .displayName("AI Classify")
                .category("AI")
                .icon("tag")
                .description("Classifies input text into categories using AI.")
                .inputs(List.of(
                        NodeInputPort.builder().name("text").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("policyKey").type("STRING").defaultValue("BALANCED").build()
                ))
                .outputs(List.of(NodeOutputPort.builder().name("category").type("STRING").build()))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String text = request.getConfiguration().getString("text", "");
        String policyKey = request.getConfiguration().getString("policyKey", "BALANCED");

        String category = classificationService.classify("GENERAL", text, policyKey);
        return NodeExecutionResult.success(Map.of("category", category));
    }
}
