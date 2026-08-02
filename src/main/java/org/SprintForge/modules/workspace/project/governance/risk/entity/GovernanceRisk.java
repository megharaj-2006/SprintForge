package org.SprintForge.modules.workspace.project.governance.risk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskCategory;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskImpact;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskProbability;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "GovernanceRisk")
@Table(name = "project_risks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GovernanceRisk extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private RiskCategory category = RiskCategory.TECHNICAL;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RiskStatus status = RiskStatus.IDENTIFIED;

    @Enumerated(EnumType.STRING)
    @Column(name = "probability", nullable = false)
    private RiskProbability probability = RiskProbability.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(name = "impact", nullable = false)
    private RiskImpact impact = RiskImpact.MEDIUM;

    @Column(name = "severity")
    private String severity = "MEDIUM";

    @Column(name = "risk_score")
    private Integer riskScore = 4;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "identified_date")
    private LocalDate identifiedDate = LocalDate.now();

    @Column(name = "target_mitigation_date")
    private LocalDate targetMitigationDate;

    @Column(name = "resolved_date")
    private LocalDateTime resolvedDate;

    @Column(name = "mitigation_plan", columnDefinition = "TEXT")
    private String mitigationPlan;

    @Column(name = "contingency_plan", columnDefinition = "TEXT")
    private String contingencyPlan;

    @Column(name = "trigger_conditions", columnDefinition = "TEXT")
    private String triggerConditions;

    @Column(name = "is_archived")
    private Boolean isArchived = false;

    public void calculateRiskScore() {
        int probVal = probability != null ? probability.getValue() : 2;
        int impVal = impact != null ? impact.getValue() : 2;
        this.riskScore = probVal * impVal;
        if (this.riskScore >= 9) {
            this.severity = "CRITICAL";
        } else if (this.riskScore >= 6) {
            this.severity = "HIGH";
        } else if (this.riskScore >= 3) {
            this.severity = "MEDIUM";
        } else {
            this.severity = "LOW";
        }
    }
}
