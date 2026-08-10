package org.SprintForge.modules.ai.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class MockOpenAIClient implements AIProviderClient {

    @Override
    public String getProviderKey() {
        return "openai";
    }

    @Override
    public AIResponse execute(AIRequest request) {
        log.info("[MOCK OPENAI CLIENT] Executing capability {} with prompt: {}", request.getCapabilityKey(), request.getPrompt());
        
        TokenUsage usage = TokenUsage.builder()
                .promptTokens(45)
                .completionTokens(60)
                .totalTokens(105)
                .build();

        return AIResponse.builder()
                .content("[Mock OpenAI Response] Processed capability: " + request.getCapabilityKey() + " -> " + request.getPrompt())
                .usage(usage)
                .latencyMs(120)
                .estimatedCost(0.00035)
                .finishReason("stop")
                .providerKey(getProviderKey())
                .modelKey(request.getModelKey() != null ? request.getModelKey() : "gpt-4o")
                .providerMetadata(Map.of("simulated", true, "offlineMode", true))
                .build();
    }

    @Override
    public boolean isHealthy() {
        return true;
    }
}
