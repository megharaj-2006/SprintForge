package org.SprintForge.modules.integration.monitoring;

import java.util.Map;

public interface IntegrationMonitoringService {
    Map<String, Object> getHealthStatus();
    Map<String, Object> getStatistics();
}
