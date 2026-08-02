package org.SprintForge.modules.workspace.project.insights.resource.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.insights.metrics.dto.ProjectMetricsResponse;
import org.SprintForge.modules.workspace.project.insights.metrics.service.ProjectMetricsService;
import org.SprintForge.modules.workspace.project.insights.resource.dto.CapacityPlanningResponse;
import org.SprintForge.modules.workspace.project.insights.resource.dto.ResourceAllocationResponse;
import org.SprintForge.modules.workspace.project.insights.resource.dto.UtilizationResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResourceAllocationServiceImpl implements ResourceAllocationService {

    private final ProjectMetricsService projectMetricsService;

    @Override
    @Transactional(readOnly = true)
    public ResourceAllocationResponse getResourceAllocation(Long projectId) {
        ProjectMetricsResponse metrics = projectMetricsService.getProjectMetrics(projectId);

        long teamSize = Math.max(1, metrics.getTeamSize());
        double capacityHours = teamSize * 40.0;
        double assignedHours = metrics.getOpenTasks() * 8.0;
        double utilization = capacityHours > 0 ? (assignedHours / capacityHours) * 100.0 : 0.0;

        Map<String, Double> roles = new HashMap<>();
        roles.put("DEVELOPER", assignedHours * 0.6);
        roles.put("DESIGNER", assignedHours * 0.15);
        roles.put("QA", assignedHours * 0.15);
        roles.put("DEVOPS", assignedHours * 0.1);

        return ResourceAllocationResponse.builder()
                .projectId(projectId)
                .totalMembers(teamSize)
                .totalAssignedHours(assignedHours)
                .totalCapacityHours(capacityHours)
                .overallUtilizationPercentage(utilization)
                .roleAllocationsHours(roles)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CapacityPlanningResponse getCapacityPlanning(Long projectId) {
        ResourceAllocationResponse alloc = getResourceAllocation(projectId);

        double margin = alloc.getTotalCapacityHours() - alloc.getTotalAssignedHours();
        String status = margin >= 0 ? (margin > 40.0 ? "SURPLUS" : "BALANCED") : "DEFICIT";

        return CapacityPlanningResponse.builder()
                .projectId(projectId)
                .availableCapacityHours(alloc.getTotalCapacityHours())
                .requiredCapacityHours(alloc.getTotalAssignedHours())
                .netCapacityMarginHours(margin)
                .capacityStatus(status)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UtilizationResponse getUtilization(Long projectId) {
        ResourceAllocationResponse alloc = getResourceAllocation(projectId);

        long overallocated = alloc.getOverallUtilizationPercentage() > 100.0 ? 1L : 0L;
        long underutilized = alloc.getOverallUtilizationPercentage() < 50.0 ? 1L : 0L;
        long optimal = Math.max(0, alloc.getTotalMembers() - overallocated - underutilized);

        return UtilizationResponse.builder()
                .projectId(projectId)
                .overallocatedMembersCount(overallocated)
                .underutilizedMembersCount(underutilized)
                .optimalMembersCount(optimal)
                .averageUtilizationPercentage(alloc.getOverallUtilizationPercentage())
                .build();
    }
}
