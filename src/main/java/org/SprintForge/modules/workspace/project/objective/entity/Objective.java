package org.SprintForge.modules.workspace.project.objective.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.objective.entity.enums.ObjectiveStatus;

import java.time.LocalDate;

@Entity(name = "StrategicObjective")
@Table(name = "objectives")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Objective extends SoftDeleteEntity {

    @Column(name = "goal_id", nullable = false)
    private Long goalId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "owner_id")
    private Long ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ObjectiveStatus status = ObjectiveStatus.NOT_STARTED;

    @Column(name = "weight")
    private Double weight = 1.0;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "target_date")
    private LocalDate targetDate;
}
