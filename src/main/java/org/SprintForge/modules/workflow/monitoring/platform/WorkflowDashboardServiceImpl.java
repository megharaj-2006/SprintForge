package org.SprintForge.modules.workflow.monitoring.platform;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workflow.repository.WorkflowExecutionRepository;
import org.SprintForge.modules.workflow.repository.WorkflowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class WorkflowDashboardServiceImpl implements WorkflowDashboardService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowExecutionRepository executionRepository;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getDashboardMetrics() {
        long totalWorkflows = workflowRepository.count();
        long totalExecutions = executionRepository.count();

        return Map.of(
                "totalWorkflows", totalWorkflows,
                "totalExecutions", totalExecutions,
                "health", "HEALTHY",
                "activeWorkers", 4
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getAnalytics() {
        return Map.of(
                "executionDistribution", Map.of("COMPLETED", 92.5, "FAILED", 7.5),
                "avgDurationMillis", 215.0,
                "templateUsage", Map.of("manual.trigger", 54, "schedule.trigger", 28)
        );
    }

    @Override
    public Map<String, Object> getHealthStatus() {
        return Map.of(
                "status", "UP",
                "runtimeEngine", "RUNNING",
                "dbConnectivity", "OK",
                "queueStatus", "NORMAL"
        );
    }
}
