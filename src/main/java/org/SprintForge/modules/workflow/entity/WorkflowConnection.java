package org.SprintForge.modules.workflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;
import org.SprintForge.modules.workflow.entity.enums.BranchLabel;

import java.time.LocalDateTime;

@Entity
@Table(name = "workflow_connections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowConnection extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_version_id", nullable = false)
    private WorkflowVersion workflowVersion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_node_id", nullable = false)
    private WorkflowNode sourceNode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_node_id", nullable = false)
    private WorkflowNode targetNode;

    @Enumerated(EnumType.STRING)
    @Column(name = "branch_label", nullable = false)
    private BranchLabel branchLabel = BranchLabel.DEFAULT;

    @Column(name = "priority", nullable = false)
    private Integer priority = 0;

    @Column(name = "condition_expression", columnDefinition = "TEXT")
    private String conditionExpression;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
