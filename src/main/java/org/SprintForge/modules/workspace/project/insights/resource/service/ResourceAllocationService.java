package org.SprintForge.modules.workspace.project.insights.resource.service;

import org.SprintForge.modules.workspace.project.insights.resource.dto.CapacityPlanningResponse;
import org.SprintForge.modules.workspace.project.insights.resource.dto.ResourceAllocationResponse;
import org.SprintForge.modules.workspace.project.insights.resource.dto.UtilizationResponse;

public interface ResourceAllocationService {
    ResourceAllocationResponse getResourceAllocation(Long projectId);
    CapacityPlanningResponse getCapacityPlanning(Long projectId);
    UtilizationResponse getUtilization(Long projectId);
}
