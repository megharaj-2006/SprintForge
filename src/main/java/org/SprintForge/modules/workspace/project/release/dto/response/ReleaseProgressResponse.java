package org.SprintForge.modules.workspace.project.release.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseProgressResponse {

    private Long releaseId;
    private String releaseName;
    private String version;
    private String status;
    private Double progressPercentage;
    private Integer totalTasks;
    private Integer completedTasks;
    private Integer openTasks;
    private Integer totalSprints;
    private Integer totalMilestones;
    private LocalDate plannedReleaseDate;
    private Long daysRemaining;
}
