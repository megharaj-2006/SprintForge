package org.SprintForge.modules.workspace.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "project_automations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAutomation extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "trigger_type")
    private String triggerType;

    @Column(name = "condition_data", columnDefinition = "TEXT")
    private String condition;

    @Column(name = "action_data", columnDefinition = "TEXT")
    private String action;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;
}

