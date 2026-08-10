package org.SprintForge.modules.workflow.nodes.ai;

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
public class AIContext {
    private String model;
    private double temperature;
    private int maxTokens;
    @Builder.Default
    private Map<String, Object> systemPrompts = new HashMap<>();
}
