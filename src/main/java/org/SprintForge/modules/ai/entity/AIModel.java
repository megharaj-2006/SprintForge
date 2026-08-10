package org.SprintForge.modules.ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_models")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AIModel extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private AIProvider provider;

    @Column(name = "model_key", nullable = false)
    private String modelKey;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "capabilities")
    private String capabilities;

    @Column(name = "context_window", nullable = false)
    private Integer contextWindow = 8192;

    @Column(name = "max_output_tokens", nullable = false)
    private Integer maxOutputTokens = 4096;

    @Column(name = "supports_streaming", nullable = false)
    private Boolean supportsStreaming = true;

    @Column(name = "supports_vision", nullable = false)
    private Boolean supportsVision = false;

    @Column(name = "supports_function_calling", nullable = false)
    private Boolean supportsFunctionCalling = true;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
