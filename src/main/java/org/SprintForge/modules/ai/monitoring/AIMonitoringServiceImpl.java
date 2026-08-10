package org.SprintForge.modules.ai.monitoring;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.dto.AIStatisticsResponse;
import org.SprintForge.modules.ai.entity.AIExecution;
import org.SprintForge.modules.ai.provider.AIProviderHealthChecker;
import org.SprintForge.modules.ai.repository.AIExecutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIMonitoringServiceImpl implements AIMonitoringService {

    private final AIExecutionRepository executionRepository;
    private final AIProviderHealthChecker healthChecker;

    @Override
    @Transactional(readOnly = true)
    public AIStatisticsResponse getStatistics() {
        List<AIExecution> executions = executionRepository.findAll();
        long total = executions.size();
        long totalTokens = executions.stream().mapToLong(e -> (e.getInputTokens() != null ? e.getInputTokens() : 0) + (e.getOutputTokens() != null ? e.getOutputTokens() : 0)).sum();
        double totalCost = executions.stream().mapToDouble(e -> e.getEstimatedCost() != null ? e.getEstimatedCost() : 0.0).sum();
        double avgLatency = executions.stream().filter(e -> e.getDurationMs() != null).mapToLong(AIExecution::getDurationMs).average().orElse(0.0);

        Map<String, Boolean> health = healthChecker.checkAllHealth();

        return AIStatisticsResponse.builder()
                .totalExecutions(total)
                .totalTokens(totalTokens)
                .totalEstimatedCost(totalCost)
                .averageLatencyMs(avgLatency)
                .providerHealth(health)
                .build();
    }
}
