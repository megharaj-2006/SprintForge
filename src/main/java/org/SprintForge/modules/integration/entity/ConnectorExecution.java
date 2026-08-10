package org.SprintForge.modules.integration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "connector_executions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectorExecution extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "connection_id")
    private IntegrationConnection connection;

    @Column(name = "capability_key", nullable = false)
    private String capabilityKey;

    @Column(name = "workflow_execution_id")
    private Long workflowExecutionId;

    @Column(name = "status", nullable = false)
    private String status = "COMPLETED";

    @Column(name = "started_at", nullable = false, updatable = false)
    private LocalDateTime startedAt = LocalDateTime.now();

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "duration_ms")
    private Long durationMs;

    @Column(name = "request_metadata", columnDefinition = "TEXT")
    private String requestMetadata;

    @Column(name = "response_metadata", columnDefinition = "TEXT")
    private String responseMetadata;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
}
