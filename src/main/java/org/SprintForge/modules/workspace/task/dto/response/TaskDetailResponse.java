package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetailResponse {

    private Long id;
    private Long workspaceId;
    private Long projectId;
    private String projectName;
    private Long sprintId;
    private String sprintName;
    private Long epicId;
    private String epicName;
    private Long parentTaskId;
    private String taskNumber;
    private String title;
    private String description;
    private TaskType type;
    private Long statusId;
    private String statusName;
    private Long priorityId;
    private String priorityName;
    private Long reporterId;
    private String reporterName;
    private Long creatorId;
    private String creatorName;
    private Long assigneeId;
    private String assigneeName;
    private String assigneeAvatarUrl;
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
    private Integer subtaskCount;
    private Integer attachmentCount;
    private Integer commentCount;
    private List<String> labels;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
