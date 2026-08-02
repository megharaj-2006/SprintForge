package org.SprintForge.modules.workspace.project.governance.decision.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.governance.decision.entity.enums.DecisionStatus;

import java.time.LocalDate;

@Entity(name = "GovernanceDecision")
@Table(name = "project_decisions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GovernanceDecision extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "problem_statement", columnDefinition = "TEXT")
    private String problemStatement;

    @Column(name = "decision", columnDefinition = "TEXT")
    private String decision;

    @Column(name = "alternatives", columnDefinition = "TEXT")
    private String alternatives;

    @Column(name = "reasoning", columnDefinition = "TEXT")
    private String reasoning;

    @Column(name = "impact", columnDefinition = "TEXT")
    private String impact;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DecisionStatus status = DecisionStatus.DRAFT;

    @Column(name = "decision_date")
    private LocalDate decisionDate = LocalDate.now();

    @Column(name = "owner_id")
    private Long ownerId;
}
