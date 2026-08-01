package org.SprintForge.modules.workspace.epic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "epics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Epic extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "parent_epic_id")
    private Long parentEpicId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "status")
    private String status = "PLANNED"; // PLANNED, IN_PROGRESS, COMPLETED, ARCHIVED

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "progress_percentage")
    private Double progressPercentage = 0.0;

    @Column(name = "estimated_story_points")
    private Integer estimatedStoryPoints = 0;

    @Column(name = "completed_story_points")
    private Integer completedStoryPoints = 0;

    @Column(name = "is_archived")
    private Boolean isArchived = false;
}
