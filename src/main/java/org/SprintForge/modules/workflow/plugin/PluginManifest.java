package org.SprintForge.modules.workflow.plugin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "plugin_manifests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PluginManifest extends BaseEntity {

    @Column(name = "package_id", nullable = false, unique = true)
    private String packageId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "plugin_version", nullable = false)
    private String pluginVersion = "1.0.0";

    @Column(name = "manifest_json", nullable = false, columnDefinition = "TEXT")
    private String manifestJson;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "installed_at", nullable = false, updatable = false)
    private LocalDateTime installedAt = LocalDateTime.now();
}
