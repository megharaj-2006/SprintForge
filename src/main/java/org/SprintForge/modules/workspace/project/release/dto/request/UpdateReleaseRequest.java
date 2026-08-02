package org.SprintForge.modules.workspace.project.release.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.release.entity.enums.ReleaseStatus;
import org.SprintForge.modules.workspace.project.release.entity.enums.ReleaseType;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReleaseRequest {

    @Size(min = 2, max = 100, message = "Release name must be between 2 and 100 characters")
    private String name;

    private String description;
    private ReleaseType releaseType;
    private ReleaseStatus status;
    private LocalDate plannedStart;
    private LocalDate plannedReleaseDate;
    private LocalDate actualReleaseDate;
    private Long ownerId;
    private String releaseNotes;
    private String color;
}
