package org.SprintForge.modules.ai.provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIResponse {
    private String content;
    private TokenUsage usage;
    private long latencyMs;
    private double estimatedCost;
    private String finishReason;
    private String providerKey;
    private String modelKey;
    @Builder.Default
    private Map<String, Object> providerMetadata = new HashMap<>();
}
