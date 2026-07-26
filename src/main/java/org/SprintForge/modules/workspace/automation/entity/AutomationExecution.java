package org.SprintForge.modules.workspace.automation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.automation.entity.enums.AutomationExecutionStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "automation_executions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutomationExecution extends SoftDeleteEntity {

    @Column(name = "automation_rule_id", nullable = false)
    private Long automationRuleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AutomationExecutionStatus status = AutomationExecutionStatus.SUCCESS;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "execution_time_ms")
    private Long executionTimeMs;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "log_data", columnDefinition = "TEXT")
    private String logData;
}

