package org.SprintForge.modules.ai.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.orchestration.PromptOrchestrator;
import org.SprintForge.modules.ai.provider.AIRequest;
import org.SprintForge.modules.ai.provider.AIResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractionServiceImpl implements ExtractionService {

    private final PromptOrchestrator promptOrchestrator;

    @Override
    public String extract(String entityType, String textContent, String policyKey) {
        AIRequest request = AIRequest.builder()
                .capabilityKey("ai.extract")
                .systemPrompt("You are an expert AI entity extractor for target entities: " + (entityType != null ? entityType : "ALL"))
                .prompt("Extract structured entities from the following text:\n" + textContent)
                .build();

        AIResponse response = promptOrchestrator.orchestrateExecution(request, policyKey);
        return response.getContent();
    }
}
