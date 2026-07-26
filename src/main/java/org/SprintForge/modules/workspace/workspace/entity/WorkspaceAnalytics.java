package org.SprintForge.modules.workspace.workspace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "workspace_analytics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceAnalytics extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false, unique = true)
    private Long workspaceId;

    @Column(name = "total_projects")
    private Long totalProjects = 0L;

    @Column(name = "total_members")
    private Long totalMembers = 0L;

    @Column(name = "total_tasks")
    private Long totalTasks = 0L;

    @Column(name = "completed_tasks")
    private Long completedTasks = 0L;

    @Column(name = "overdue_tasks")
    private Long overdueTasks = 0L;

    @Column(name = "active_sprints")
    private Long activeSprints = 0L;

    @Column(name = "completed_sprints")
    private Long completedSprints = 0L;

    @Column(name = "average_task_completion_time")
    private Double averageTaskCompletionTime = 0.0;

    @Column(name = "average_sprint_velocity")
    private Double averageSprintVelocity = 0.0;

    @Column(name = "storage_used")
    private Long storageUsed = 0L;

    @Column(name = "last_calculated_at")
    private LocalDateTime lastCalculatedAt;
}

