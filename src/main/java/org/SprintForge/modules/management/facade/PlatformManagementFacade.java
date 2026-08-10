package org.SprintForge.modules.management.facade;

import org.SprintForge.modules.management.sdk.HealthReport;
import org.SprintForge.modules.management.sdk.ManagementDescriptor;
import org.SprintForge.modules.management.sdk.MetricsSnapshot;

import java.util.List;
import java.util.Map;

public interface PlatformManagementFacade {
    List<ManagementDescriptor> getModules();
    Map<String, HealthReport> getAggregatedHealth();
    Map<String, MetricsSnapshot> getAggregatedMetrics();
    Map<String, Object> getDashboardOverview();
}
