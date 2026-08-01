package org.SprintForge.modules.workspace.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.entity.enums.MilestoneStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a Milestone within a Project.
 *
 * A Milestone is a time-bound checkpoint used to track major goals or phases
 * inside a project. Each Milestone may contain one or more Tasks that must be
 * completed to consider the milestone done.
 *
 * Aggregate ownership: Project module.
 * Parent: Project (1 project → N milestones).
 * Children: Tasks (1 milestone → N tasks, via Task.milestoneId).
 */
@Entity
@Table(name = "milestones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Milestone extends SoftDeleteEntity {

    // --- Business Fields ---

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MilestoneStatus status = MilestoneStatus.ACTIVE;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived = false;

    @Column(name = "progress_percentage")
    private Double progressPercentage = 0.0;

    // --- Business Methods ---

    /**
     * Marks this milestone as completed.
     * Sets the status to COMPLETED and records the completion timestamp.
     */
    public void complete() {
        this.status = MilestoneStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        this.progressPercentage = 100.0;
    }

    /**
     * Archives this milestone.
     * An archived milestone is hidden from active views but not deleted.
     */
    public void archive() {
        this.isArchived = true;
        this.status = MilestoneStatus.ARCHIVED;
    }

    /**
     * Restores an archived milestone back to ACTIVE status.
     */
    public void restoreFromArchive() {
        this.isArchived = false;
        this.status = MilestoneStatus.ACTIVE;
    }

    /**
     * Updates the stored progress percentage from a calculated value.
     *
     * @param percentage a value between 0.0 and 100.0
     */
    public void updateProgress(double percentage) {
        this.progressPercentage = Math.min(100.0, Math.max(0.0, percentage));
    }
}
