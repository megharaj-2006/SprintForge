package org.SprintForge.modules.management;

import org.SprintForge.modules.management.facade.ManagementRegistry;
import org.SprintForge.modules.management.facade.PlatformManagementFacade;
import org.SprintForge.modules.management.facade.PlatformManagementFacadeImpl;
import org.SprintForge.modules.management.module.WorkflowPlatformManagedModule;
import org.SprintForge.modules.management.sdk.HealthReport;
import org.SprintForge.modules.management.sdk.ManagementDescriptor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ManagementFacadeTest {

    @Test
    void testFacadeModuleAggregation() {
        WorkflowPlatformManagedModule workflowModule = new WorkflowPlatformManagedModule();
        ManagementRegistry registry = new ManagementRegistry(List.of(workflowModule));
        PlatformManagementFacade facade = new PlatformManagementFacadeImpl(registry);

        List<ManagementDescriptor> descriptors = facade.getModules();
        assertNotNull(descriptors);
        assertEquals(1, descriptors.size());
        assertEquals("workflow", descriptors.get(0).getModuleId());

        Map<String, HealthReport> healthMap = facade.getAggregatedHealth();
        assertTrue(healthMap.containsKey("workflow"));
        assertEquals("UP", healthMap.get("workflow").getStatus().name());

        Map<String, Object> dashboard = facade.getDashboardOverview();
        assertEquals("HEALTHY", dashboard.get("platformStatus"));
    }
}
