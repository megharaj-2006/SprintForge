package org.SprintForge.modules.ai.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.orchestration.PromptOrchestrator;
import org.SprintForge.modules.ai.provider.AIRequest;
import org.SprintForge.modules.ai.provider.AIResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassificationServiceImpl implements ClassificationService {

    private final PromptOrchestrator promptOrchestrator;

    @Override
    public String classify(String classificationType, String textContent, String policyKey) {
        AIRequest request = AIRequest.builder()
                .capabilityKey("ai.classify")
                .systemPrompt("You are an expert AI classifier for type: " + (classificationType != null ? classificationType : "GENERAL"))
                .prompt("Classify the following text into priority/sentiment/category:\n" + textContent)
                .build();

        AIResponse response = promptOrchestrator.orchestrateExecution(request, policyKey);
        return response.getContent();
    }
}
