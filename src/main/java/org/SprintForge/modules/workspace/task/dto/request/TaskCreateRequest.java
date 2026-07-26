package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskType;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateRequest {

    @NotNull(message = "Workspace ID is required")
    private Long workspaceId;

    private Long projectId;
    private Long sprintId;
    private Long epicId;
    private Long parentTaskId;

    @NotBlank(message = "Task title is required")
    @Size(min = 2, max = 255, message = "Task title must be between 2 and 255 characters")
    private String title;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    private TaskType type;
    private Long statusId;
    private Long priorityId;

    private Long reporterId;
    private Long assigneeId;

    private Double estimateHours;
    private Integer storyPoints;

    private LocalDate startDate;
    private LocalDate dueDate;

    private Integer position;
    private Boolean isTemplate;
}
