package org.SprintForge.modules.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIStatisticsResponse {
    private long totalExecutions;
    private long totalTokens;
    private double totalEstimatedCost;
    private double averageLatencyMs;
    private Map<String, Boolean> providerHealth;
}
