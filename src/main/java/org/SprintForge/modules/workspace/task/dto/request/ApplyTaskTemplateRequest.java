package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyTaskTemplateRequest {

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private String taskTitleOverride;

    private Long assigneeId;

    private LocalDate dueDate;

    private Long sprintId;

    private Long milestoneId;
}
