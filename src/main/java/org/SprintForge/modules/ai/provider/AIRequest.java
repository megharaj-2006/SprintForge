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
public class AIRequest {
    private String capabilityKey;
    private String prompt;
    private String systemPrompt;
    private String providerKey;
    private String modelKey;
    @Builder.Default
    private Double temperature = 0.7;
    @Builder.Default
    private Integer maxTokens = 2048;
    private String conversationId;
    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();
    @Builder.Default
    private Map<String, Object> executionContext = new HashMap<>();
}
