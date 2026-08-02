package org.SprintForge.modules.workspace.project.insights.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceAllocationResponse {

    private Long projectId;
    private long totalMembers;
    private double totalAssignedHours;
    private double totalCapacityHours;
    private double overallUtilizationPercentage;
    private Map<String, Double> roleAllocationsHours;
}
