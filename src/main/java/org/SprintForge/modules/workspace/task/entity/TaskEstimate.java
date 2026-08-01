package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "task_estimates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskEstimate extends SoftDeleteEntity {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "estimate_type", nullable = false)
    private String estimateType; // HOURS, DAYS, STORY_POINTS, COMPLEXITY, TSHIRT_SIZE, COST_ESTIMATE

    @Column(name = "estimated_value", nullable = false)
    private Double estimatedValue;

    @Column(name = "actual_value")
    private Double actualValue;

    @Column(name = "variance")
    private Double variance; // actualValue - estimatedValue

    @Column(name = "estimated_by")
    private Long estimatedBy;
}
