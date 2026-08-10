package org.SprintForge.modules.ai.policy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_execution_policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AIExecutionPolicy extends BaseEntity {

    @Column(name = "policy_key", nullable = false, unique = true)
    private String policyKey;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "preferred_provider")
    private String preferredProvider;

    @Column(name = "preferred_model")
    private String preferredModel;

    @Column(name = "fallback_provider")
    private String fallbackProvider;

    @Column(name = "fallback_model")
    private String fallbackModel;

    @Column(name = "max_cost")
    private Double maxCost = 0.05;

    @Column(name = "max_latency_ms")
    private Integer maxLatencyMs = 5000;

    @Column(name = "privacy_level")
    private String privacyLevel = "STANDARD";

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
