package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private Long id;
    private Long workspaceId;
    private Long projectId;
    private Long sprintId;
    private Long epicId;
    private Long parentTaskId;
    private String taskNumber;
    private String title;
    private String description;
    private TaskType type;
    private Long statusId;
    private Long priorityId;
    private Long reporterId;
    private Long creatorId;
    private Long assigneeId;
    private Double estimateHours;
    private Double loggedHours;
    private Integer storyPoints;
    private Double progressPercentage;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDateTime completedAt;
    private Integer position;
    private Boolean isArchived;
    private Boolean isTemplate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
