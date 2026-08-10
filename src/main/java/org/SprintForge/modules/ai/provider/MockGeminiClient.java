package org.SprintForge.modules.ai.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class MockGeminiClient implements AIProviderClient {

    @Override
    public String getProviderKey() {
        return "gemini";
    }

    @Override
    public AIResponse execute(AIRequest request) {
        log.info("[MOCK GEMINI CLIENT] Executing capability {} with prompt: {}", request.getCapabilityKey(), request.getPrompt());

        TokenUsage usage = TokenUsage.builder()
                .promptTokens(38)
                .completionTokens(52)
                .totalTokens(90)
                .build();

        return AIResponse.builder()
                .content("[Mock Gemini Response] Processed capability: " + request.getCapabilityKey() + " -> " + request.getPrompt())
                .usage(usage)
                .latencyMs(110)
                .estimatedCost(0.00028)
                .finishReason("STOP")
                .providerKey(getProviderKey())
                .modelKey(request.getModelKey() != null ? request.getModelKey() : "gemini-1.5-pro")
                .providerMetadata(Map.of("simulated", true, "offlineMode", true))
                .build();
    }

    @Override
    public boolean isHealthy() {
        return true;
    }
}
