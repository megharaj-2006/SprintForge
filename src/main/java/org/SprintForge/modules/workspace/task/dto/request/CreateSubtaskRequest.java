package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskPriority;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.entity.enums.TaskType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubtaskRequest {

    @NotBlank(message = "Task title is required")
    @Size(min = 2, max = 255, message = "Task title must be between 2 and 255 characters")
    private String title;

    @Size(max = 10000, message = "Description must not exceed 10000 characters")
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private TaskType type;

    private LocalDateTime dueDate;

    private Double estimatedHours;

    private Integer storyPoints;
}
