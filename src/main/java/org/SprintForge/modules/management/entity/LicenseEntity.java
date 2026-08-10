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
@Table(name = "mgmt_licenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseEntity extends BaseEntity {

    @Column(name = "license_key", nullable = false, unique = true)
    private String licenseKey;

    @Column(name = "license_tier", nullable = false)
    private String licenseTier = "ENTERPRISE";

    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Column(name = "max_workspaces")
    private Integer maxWorkspaces = 100;

    @Column(name = "max_seats")
    private Integer maxSeats = 1000;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
