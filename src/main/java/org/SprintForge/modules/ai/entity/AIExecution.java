package org.SprintForge.modules.ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_executions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AIExecution extends BaseEntity {

    @Column(name = "capability_key", nullable = false)
    private String capabilityKey;

    @Column(name = "provider_key", nullable = false)
    private String providerKey;

    @Column(name = "model_key", nullable = false)
    private String modelKey;

    @Column(name = "workflow_execution_id")
    private Long workflowExecutionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prompt_template_id")
    private PromptTemplate promptTemplate;

    @Column(name = "status", nullable = false)
    private String status = "COMPLETED";

    @Column(name = "started_at", nullable = false, updatable = false)
    private LocalDateTime startedAt = LocalDateTime.now();

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "duration_ms")
    private Long durationMs;

    @Column(name = "input_tokens")
    private Integer inputTokens = 0;

    @Column(name = "output_tokens")
    private Integer outputTokens = 0;

    @Column(name = "estimated_cost")
    private Double estimatedCost = 0.0;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
}
