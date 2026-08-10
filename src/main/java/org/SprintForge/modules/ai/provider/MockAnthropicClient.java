package org.SprintForge.modules.ai.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class MockAnthropicClient implements AIProviderClient {

    @Override
    public String getProviderKey() {
        return "anthropic";
    }

    @Override
    public AIResponse execute(AIRequest request) {
        log.info("[MOCK ANTHROPIC CLIENT] Executing capability {} with prompt: {}", request.getCapabilityKey(), request.getPrompt());

        TokenUsage usage = TokenUsage.builder()
                .promptTokens(50)
                .completionTokens(70)
                .totalTokens(120)
                .build();

        return AIResponse.builder()
                .content("[Mock Anthropic Response] Processed capability: " + request.getCapabilityKey() + " -> " + request.getPrompt())
                .usage(usage)
                .latencyMs(140)
                .estimatedCost(0.00042)
                .finishReason("end_turn")
                .providerKey(getProviderKey())
                .modelKey(request.getModelKey() != null ? request.getModelKey() : "claude-3-5-sonnet")
                .providerMetadata(Map.of("simulated", true, "offlineMode", true))
                .build();
    }

    @Override
    public boolean isHealthy() {
        return true;
    }
}
