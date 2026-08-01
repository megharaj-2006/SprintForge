package org.SprintForge.modules.workspace.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleasePlanRequest {

    @NotBlank(message = "Release version is required")
    private String releaseVersion;

    private String releaseName;

    private LocalDate releaseDate;
}
