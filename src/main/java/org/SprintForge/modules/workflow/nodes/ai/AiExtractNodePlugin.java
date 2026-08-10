package org.SprintForge.modules.workflow.nodes.ai;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.service.ExtractionService;
import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AiExtractNodePlugin extends AbstractNodePlugin {

    private final ExtractionService extractionService;

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("ai.extract")
                .displayName("AI Extract")
                .category("AI")
                .icon("filter")
                .description("Extracts structured JSON entities from unstructured text.")
                .inputs(List.of(
                        NodeInputPort.builder().name("text").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("policyKey").type("STRING").defaultValue("BALANCED").build()
                ))
                .outputs(List.of(NodeOutputPort.builder().name("extractedEntities").type("STRING").build()))
                .branches(List.of(NodeBranchPort.label("SUCCESS").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String text = request.getConfiguration().getString("text", "");
        String policyKey = request.getConfiguration().getString("policyKey", "BALANCED");

        String extracted = extractionService.extract("ENTITIES", text, policyKey);
        return NodeExecutionResult.success(Map.of("extractedEntities", extracted));
    }
}
