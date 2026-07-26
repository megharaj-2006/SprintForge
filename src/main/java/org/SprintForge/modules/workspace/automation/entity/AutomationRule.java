package org.SprintForge.modules.workspace.automation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "automation_rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutomationRule extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "trigger_type")
    private String triggerType;

    @Column(name = "trigger_configuration", columnDefinition = "TEXT")
    private String triggerConfiguration;

    @Column(name = "condition_configuration", columnDefinition = "TEXT")
    private String conditionConfiguration;

    @Column(name = "action_configuration", columnDefinition = "TEXT")
    private String actionConfiguration;

    @Column(name = "execution_order")
    private Integer executionOrder = 0;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;
}

