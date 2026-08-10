package org.SprintForge.modules.workflow.monitoring.platform;

import java.util.Map;

public interface WorkflowDashboardService {
    Map<String, Object> getDashboardMetrics();
    Map<String, Object> getAnalytics();
    Map<String, Object> getHealthStatus();
}
