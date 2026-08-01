package org.SprintForge.modules.workspace.project.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.entity.enums.MilestoneStatus;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneCreateRequest {

    @NotBlank(message = "Milestone name is required")
    @Size(min = 2, max = 100, message = "Milestone name must be between 2 and 100 characters")
    private String name;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;

    /**
     * Initial status of the milestone. Defaults to ACTIVE when not provided.
     */
    private MilestoneStatus status;
}
