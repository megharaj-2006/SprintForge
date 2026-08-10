package org.SprintForge.modules.management.facade;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.management.sdk.HealthReport;
import org.SprintForge.modules.management.sdk.ManagedPlatformModule;
import org.SprintForge.modules.management.sdk.ManagementDescriptor;
import org.SprintForge.modules.management.sdk.MetricsSnapshot;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlatformManagementFacadeImpl implements PlatformManagementFacade {

    private final ManagementRegistry managementRegistry;

    @Override
    public List<ManagementDescriptor> getModules() {
        return managementRegistry.getAllModules().stream()
                .map(ManagedPlatformModule::descriptor)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, HealthReport> getAggregatedHealth() {
        Map<String, HealthReport> healthMap = new HashMap<>();
        for (ManagedPlatformModule module : managementRegistry.getAllModules()) {
            healthMap.put(module.moduleId(), module.health());
        }
        return healthMap;
    }

    @Override
    public Map<String, MetricsSnapshot> getAggregatedMetrics() {
        Map<String, MetricsSnapshot> metricsMap = new HashMap<>();
        for (ManagedPlatformModule module : managementRegistry.getAllModules()) {
            metricsMap.put(module.moduleId(), module.metrics());
        }
        return metricsMap;
    }

    @Override
    public Map<String, Object> getDashboardOverview() {
        List<ManagedPlatformModule> modules = managementRegistry.getAllModules();
        long totalModules = modules.size();
        long healthyModules = modules.stream().filter(m -> m.health().getStatus().name().equals("UP")).count();

        return Map.of(
                "totalManagedModules", totalModules,
                "healthyModules", healthyModules,
                "platformStatus", healthyModules == totalModules ? "HEALTHY" : "DEGRADED",
                "systemVersion", "3.0.0-ENTERPRISE"
        );
    }
}
