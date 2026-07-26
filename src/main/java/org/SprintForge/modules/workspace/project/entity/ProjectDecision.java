package org.SprintForge.modules.workspace.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "project_decisions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDecision extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "decision", columnDefinition = "TEXT")
    private String decision;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "decision_maker_id")
    private Long decisionMakerId;

    @Column(name = "impact")
    private String impact;
}

