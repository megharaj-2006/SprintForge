package org.SprintForge.modules.workspace.sprint.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sprints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sprint extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "goal", columnDefinition = "TEXT")
    private String goal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SprintStatus status = SprintStatus.PLANNED;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "planned_story_points")
    private Integer plannedStoryPoints = 0;

    @Column(name = "completed_story_points")
    private Integer completedStoryPoints = 0;

    @Column(name = "velocity")
    private Double velocity = 0.0;

    @Column(name = "capacity")
    private Double capacity = 0.0;

    @Column(name = "completed_task_count")
    private Integer completedTaskCount = 0;

    @Column(name = "total_task_count")
    private Integer totalTaskCount = 0;
}
