package org.SprintForge.modules.workspace.goal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "key_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyResult extends SoftDeleteEntity {

    @Column(name = "goal_id", nullable = false)
    private Long goalId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "target_value")
    private Double targetValue = 0.0;

    @Column(name = "current_value")
    private Double currentValue = 0.0;

    @Column(name = "unit")
    private String unit;

    @Column(name = "progress_percentage")
    private Double progressPercentage = 0.0;

    @Column(name = "status")
    private String status;
}

