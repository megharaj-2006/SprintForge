package org.SprintForge.modules.integration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.AuditableEntity;
import org.SprintForge.modules.integration.entity.enums.ConnectionStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "integration_connections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationConnection extends AuditableEntity {

    @Column(name = "workspace_id")
    private Long workspaceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private IntegrationProvider provider;

    @Column(name = "connection_name", nullable = false)
    private String connectionName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ConnectionStatus status = ConnectionStatus.ACTIVE;

    @Column(name = "authentication_type", nullable = false)
    private String authenticationType = "API_KEY";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credential_reference_id")
    private CredentialReference credentialReference;

    @Column(name = "base_url")
    private String baseUrl;

    @Column(name = "configuration", columnDefinition = "TEXT")
    private String configuration;

    @Column(name = "health_status")
    private String healthStatus = "HEALTHY";

    @Column(name = "last_connected")
    private LocalDateTime lastConnected;
}
