package org.SprintForge.modules.workspace.project.keyresult.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.keyresult.entity.enums.KeyResultMetricType;

@Entity(name = "StrategicKeyResult")
@Table(name = "key_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyResult extends SoftDeleteEntity {

    @Column(name = "objective_id", nullable = false)
    private Long objectiveId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "metric_type", nullable = false)
    private KeyResultMetricType metricType = KeyResultMetricType.PERCENTAGE;

    @Column(name = "target_value", nullable = false)
    private Double targetValue = 100.0;

    @Column(name = "current_value", nullable = false)
    private Double currentValue = 0.0;

    @Column(name = "unit")
    private String unit = "%";

    @Column(name = "weight")
    private Double weight = 1.0;

    @Column(name = "status")
    private String status = "IN_PROGRESS";
}
