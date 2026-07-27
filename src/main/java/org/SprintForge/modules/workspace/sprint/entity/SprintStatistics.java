package org.SprintForge.modules.workspace.sprint.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "sprint_statisticss")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SprintStatistics extends SoftDeleteEntity {

    @Column(name = "sprint_id", nullable = false)
    private Long sprintId;

    @Column(name = "total_story_points", nullable = false)
    private Integer totalStoryPoints = 0;

    @Column(name = "completed_story_points", nullable = false)
    private Integer completedStoryPoints = 0;

    @Column(name = "velocity")
    private Double velocity = 0.0;
}
