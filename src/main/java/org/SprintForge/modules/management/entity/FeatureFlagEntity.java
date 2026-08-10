package org.SprintForge.modules.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.AuditableEntity;

@Entity
@Table(name = "mgmt_feature_flags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeatureFlagEntity extends AuditableEntity {

    @Column(name = "flag_key", nullable = false, unique = true)
    private String flagKey;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "rollout_percentage")
    private Integer rolloutPercentage = 100;

    @Column(name = "scope", nullable = false)
    private String scope = "GLOBAL";
}
