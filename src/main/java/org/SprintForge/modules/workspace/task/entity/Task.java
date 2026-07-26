package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.task.entity.enums.TaskType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "sprint_id")
    private Long sprintId;

    @Column(name = "epic_id")
    private Long epicId;

    @Column(name = "parent_task_id")
    private Long parentTaskId;

    @Column(name = "task_number")
    private String taskNumber;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TaskType type = TaskType.TASK;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "priority_id")
    private Long priorityId;

    @Column(name = "reporter_id")
    private Long reporterId;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "assignee_id")
    private Long assigneeId;

    @Column(name = "estimate_hours")
    private Double estimateHours;

    @Column(name = "logged_hours")
    private Double loggedHours = 0.0;

    @Column(name = "story_points")
    private Integer storyPoints;

    @Column(name = "progress_percentage")
    private Double progressPercentage = 0.0;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "position")
    private Integer position;

    @Column(name = "is_archived")
    private Boolean isArchived = false;

    @Column(name = "is_template")
    private Boolean isTemplate = false;
}
