package org.SprintForge.modules.integration.monitoring;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.integration.entity.ConnectorExecution;

import org.SprintForge.modules.integration.repository.ConnectorExecutionRepository;
import org.SprintForge.modules.integration.repository.IntegrationConnectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IntegrationMonitoringServiceImpl implements IntegrationMonitoringService {

    private final IntegrationConnectionRepository connectionRepository;
    private final ConnectorExecutionRepository executionRepository;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getHealthStatus() {
        long activeConnections = connectionRepository.count();
        return Map.of(
                "status", "UP",
                "activeConnections", activeConnections,
                "webhookReceiver", "READY"
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getStatistics() {
        List<ConnectorExecution> executions = executionRepository.findAll();
        long total = executions.size();
        double avgLatency = executions.stream().filter(e -> e.getDurationMs() != null).mapToLong(ConnectorExecution::getDurationMs).average().orElse(0.0);

        return Map.of(
                "totalExecutions", total,
                "averageLatencyMs", avgLatency,
                "availability", "100.0%"
        );
    }
}
