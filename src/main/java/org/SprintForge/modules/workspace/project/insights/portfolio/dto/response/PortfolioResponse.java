package org.SprintForge.modules.workspace.project.insights.portfolio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponse {

    private Long id;
    private Long workspaceId;
    private String name;
    private String description;
    private Long ownerId;
    private String status;
    private List<Long> projectIds;
    private double overallProgressPercentage;
    private long totalProjects;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
