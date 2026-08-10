package org.SprintForge.modules.integration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.AuditableEntity;
import org.SprintForge.modules.integration.entity.enums.IntegrationProviderType;

@Entity
@Table(name = "integration_providers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationProvider extends AuditableEntity {

    @Column(name = "provider_key", nullable = false, unique = true)
    private String providerKey;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", nullable = false)
    private IntegrationProviderType providerType = IntegrationProviderType.REST;

    @Column(name = "category", nullable = false)
    private String category = "GENERAL";

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "provider_version", nullable = false)
    private String providerVersion = "1.0.0";

    @Column(name = "icon")
    private String icon;

    @Column(name = "documentation_url")
    private String documentationUrl;
}
