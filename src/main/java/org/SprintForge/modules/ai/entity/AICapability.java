package org.SprintForge.modules.ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;
import org.SprintForge.modules.ai.entity.enums.CapabilityCategory;

@Entity
@Table(name = "ai_capabilities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AICapability extends BaseEntity {

    @Column(name = "capability_key", nullable = false, unique = true)
    private String capabilityKey;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CapabilityCategory category = CapabilityCategory.GENERATION;

    @Column(name = "default_provider_key")
    private String defaultProviderKey;

    @Column(name = "default_model_key")
    private String defaultModelKey;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
}
