package org.SprintForge.modules.workspace.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectRiskProbability;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectRiskSeverity;

@Entity
@Table(name = "project_risks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRisk extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private ProjectRiskSeverity severity = ProjectRiskSeverity.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(name = "probability")
    private ProjectRiskProbability probability = ProjectRiskProbability.MEDIUM;

    @Column(name = "impact")
    private String impact;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "mitigation_plan", columnDefinition = "TEXT")
    private String mitigationPlan;

    @Column(name = "status")
    private String status;
}

