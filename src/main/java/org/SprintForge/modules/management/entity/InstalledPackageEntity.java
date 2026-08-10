package org.SprintForge.modules.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "mgmt_installed_packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstalledPackageEntity extends BaseEntity {

    @Column(name = "package_key", nullable = false, unique = true)
    private String packageKey;

    @Column(name = "package_name", nullable = false)
    private String packageName;

    @Column(name = "version_string", nullable = false)
    private String versionString = "1.0.0";

    @Column(name = "status", nullable = false)
    private String status = "ENABLED";

    @Column(name = "installed_by")
    private String installedBy;

    @Column(name = "installed_at", nullable = false, updatable = false)
    private LocalDateTime installedAt = LocalDateTime.now();
}
