package org.SprintForge.modules.workflow.governance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "workflow_policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowPolicy extends BaseEntity {

    @Column(name = "workspace_id")
    private Long workspaceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "require_approval_before_publish", nullable = false)
    private Boolean requireApprovalBeforePublish = false;

    @Column(name = "max_execution_duration_seconds")
    private Long maxExecutionDurationSeconds = 3600L;

    @Column(name = "max_parallelism")
    private Integer maxParallelism = 10;

    @Column(name = "max_memory_mb")
    private Integer maxMemoryMb = 512;

    @Column(name = "rate_limit_per_minute")
    private Integer rateLimitPerMinute = 60;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
