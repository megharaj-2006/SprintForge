package org.SprintForge.modules.management.sdk;

import java.util.List;
import java.util.Map;

public interface ManagedPlatformModule {
    String moduleId();
    String moduleName();
    ManagementDescriptor descriptor();
    HealthReport health();
    MetricsSnapshot metrics();
    Map<String, Object> configuration();
    List<ManagementCapability> capabilities();
}
