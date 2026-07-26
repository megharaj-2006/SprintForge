package org.SprintForge.modules.workspace.template.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "feature_flags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeatureFlag extends SoftDeleteEntity {

    @Column(name = "workspace_id")
    private Long workspaceId;

    @Column(name = "feature_name", nullable = false)
    private String featureName;

    @Column(name = "enabled")
    private Boolean enabled = false;

    @Column(name = "configuration", columnDefinition = "TEXT")
    private String configuration;
}

