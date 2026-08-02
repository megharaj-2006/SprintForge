package org.SprintForge.modules.workspace.project.insights.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapacityPlanningResponse {

    private Long projectId;
    private double availableCapacityHours;
    private double requiredCapacityHours;
    private double netCapacityMarginHours;
    private String capacityStatus; // BALANCED, DEFICIT, SURPLUS
}
