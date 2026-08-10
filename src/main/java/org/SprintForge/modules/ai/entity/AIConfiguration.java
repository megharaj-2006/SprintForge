package org.SprintForge.modules.ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;

@Entity
@Table(name = "ai_configurations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AIConfiguration extends BaseEntity {

    @Column(name = "workspace_id", unique = true)
    private Long workspaceId;

    @Column(name = "default_provider_key", nullable = false)
    private String defaultProviderKey = "openai";

    @Column(name = "default_model_key", nullable = false)
    private String defaultModelKey = "gpt-4o";

    @Column(name = "max_tokens")
    private Integer maxTokens = 2048;

    @Column(name = "temperature")
    private Double temperature = 0.7;

    @Column(name = "timeout_seconds")
    private Integer timeoutSeconds = 30;

    @Column(name = "retry_policy")
    private String retryPolicy = "EXPONENTIAL_BACKOFF";

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
}
