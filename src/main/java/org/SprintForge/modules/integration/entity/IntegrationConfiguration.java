package org.SprintForge.modules.integration.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;

@Entity
@Table(name = "integration_configurations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationConfiguration extends BaseEntity {

    @Column(name = "workspace_id", unique = true)
    private Long workspaceId;

    @Column(name = "default_timeout_seconds")
    private Integer defaultTimeoutSeconds = 30;

    @Column(name = "retry_policy")
    private String retryPolicy = "EXPONENTIAL_BACKOFF";

    @Column(name = "rate_limit_per_minute")
    private Integer rateLimitPerMinute = 120;

    @Column(name = "logging_enabled", nullable = false)
    private Boolean loggingEnabled = true;

    @Column(name = "monitoring_enabled", nullable = false)
    private Boolean monitoringEnabled = true;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
}
