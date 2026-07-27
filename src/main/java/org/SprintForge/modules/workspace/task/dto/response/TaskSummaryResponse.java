package org.SprintForge.modules.workspace.task.dto.response;

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
public class TaskSummaryResponse {
    private Long id;
    private Long projectId;
    private Long sprintId;
    private String identifier;
    private String title;
    private TaskStatus status;
    private TaskPriority priority;
    private TaskType type;
    private LocalDateTime dueDate;
    private Double estimatedHours;
    private Double actualHours;
    private Integer storyPoints;
    private Boolean archived;
}
