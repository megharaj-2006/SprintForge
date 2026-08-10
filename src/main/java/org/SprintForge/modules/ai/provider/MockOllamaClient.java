package org.SprintForge.modules.ai.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class MockOllamaClient implements AIProviderClient {

    @Override
    public String getProviderKey() {
        return "ollama";
    }

    @Override
    public AIResponse execute(AIRequest request) {
        log.info("[MOCK OLLAMA CLIENT] Executing capability {} with prompt: {}", request.getCapabilityKey(), request.getPrompt());

        TokenUsage usage = TokenUsage.builder()
                .promptTokens(30)
                .completionTokens(40)
                .totalTokens(70)
                .build();

        return AIResponse.builder()
                .content("[Mock Ollama Response] Processed capability: " + request.getCapabilityKey() + " -> " + request.getPrompt())
                .usage(usage)
                .latencyMs(80)
                .estimatedCost(0.0) // Local model free cost
                .finishReason("stop")
                .providerKey(getProviderKey())
                .modelKey(request.getModelKey() != null ? request.getModelKey() : "llama3")
                .providerMetadata(Map.of("simulated", true, "offlineMode", true, "local", true))
                .build();
    }

    @Override
    public boolean isHealthy() {
        return true;
    }
}
