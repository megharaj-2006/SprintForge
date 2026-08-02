package org.SprintForge.modules.workspace.project.insights.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilizationResponse {

    private Long projectId;
    private long overallocatedMembersCount;
    private long underutilizedMembersCount;
    private long optimalMembersCount;
    private double averageUtilizationPercentage;
}
