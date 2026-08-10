package org.SprintForge.modules.ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.AuditableEntity;
import org.SprintForge.modules.ai.entity.enums.ProviderType;

@Entity
@Table(name = "ai_providers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AIProvider extends AuditableEntity {

    @Column(name = "provider_key", nullable = false, unique = true)
    private String providerKey;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", nullable = false)
    private ProviderType providerType = ProviderType.OPENAI;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "priority", nullable = false)
    private Integer priority = 1;

    @Column(name = "base_url")
    private String baseUrl;

    @Column(name = "authentication_type", nullable = false)
    private String authenticationType = "BEARER_TOKEN";

    @Column(name = "default_model")
    private String defaultModel;

    @Column(name = "configuration", columnDefinition = "TEXT")
    private String configuration;
}
