package org.SprintForge.modules.ai.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.orchestration.PromptOrchestrator;
import org.SprintForge.modules.ai.provider.AIRequest;
import org.SprintForge.modules.ai.provider.AIResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummarizationServiceImpl implements SummarizationService {

    private final PromptOrchestrator promptOrchestrator;

    @Override
    public String summarize(String contentType, String textContent, String policyKey) {
        AIRequest request = AIRequest.builder()
                .capabilityKey("ai.summarize")
                .systemPrompt("You are an expert AI summarizer for " + (contentType != null ? contentType : "general text"))
                .prompt("Summarize the following content:\n" + textContent)
                .build();

        AIResponse response = promptOrchestrator.orchestrateExecution(request, policyKey);
        return response.getContent();
    }
}
