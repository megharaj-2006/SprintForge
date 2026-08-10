package org.SprintForge.modules.workflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;
import org.SprintForge.modules.workflow.entity.enums.NodeExecutionStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "node_executions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NodeExecution extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_execution_id", nullable = false)
    private WorkflowExecution workflowExecution;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_node_id", nullable = false)
    private WorkflowNode workflowNode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private NodeExecutionStatus status = NodeExecutionStatus.PENDING;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "input", columnDefinition = "TEXT")
    private String input;

    @Column(name = "output", columnDefinition = "TEXT")
    private String output;

    @Column(name = "error", columnDefinition = "TEXT")
    private String error;
}
