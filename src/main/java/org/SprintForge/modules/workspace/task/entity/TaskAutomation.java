package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "task_automations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskAutomation extends SoftDeleteEntity {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "rule_name", nullable = false)
    private String ruleName;

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

