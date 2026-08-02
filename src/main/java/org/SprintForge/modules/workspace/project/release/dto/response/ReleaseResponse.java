package org.SprintForge.modules.workspace.project.release.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.release.entity.enums.ReleaseStatus;
import org.SprintForge.modules.workspace.project.release.entity.enums.ReleaseType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseResponse {

    private Long id;
    private Long projectId;
    private String name;
    private String version;
    private String description;
    private ReleaseType releaseType;
    private ReleaseStatus status;
    private LocalDate plannedStart;
    private LocalDate plannedReleaseDate;
    private LocalDate actualReleaseDate;
    private Long ownerId;
    private String releaseNotes;
    private String color;
    private Double progressPercentage;
    private Integer totalTasks;
    private Integer completedTasks;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
