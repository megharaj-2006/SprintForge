package org.SprintForge.modules.workspace.project.release.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.release.entity.enums.ReleaseType;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReleaseRequest {

    @NotBlank(message = "Release name is required")
    @Size(min = 2, max = 100, message = "Release name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Release version is required")
    @Size(min = 1, max = 30, message = "Release version must be between 1 and 30 characters")
    private String version;

    private String description;
    private ReleaseType releaseType;
    private LocalDate plannedStart;
    private LocalDate plannedReleaseDate;
    private Long ownerId;
    private String releaseNotes;
    private String color;
}
