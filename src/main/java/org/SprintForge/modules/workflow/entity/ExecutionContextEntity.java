package org.SprintForge.modules.workflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "execution_contexts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionContextEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_execution_id", nullable = false, unique = true)
    private WorkflowExecution workflowExecution;

    @Column(name = "variables", columnDefinition = "TEXT")
    private String variables;

    @Column(name = "runtime_state", columnDefinition = "TEXT")
    private String runtimeState;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "correlation_id")
    private String correlationId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
