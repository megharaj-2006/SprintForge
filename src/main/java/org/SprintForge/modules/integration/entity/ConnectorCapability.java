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
@Table(name = "connector_capabilities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectorCapability extends BaseEntity {

    @Column(name = "capability_key", nullable = false, unique = true)
    private String capabilityKey;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category", nullable = false)
    private String category = "GENERAL";

    @Column(name = "supported_operations")
    private String supportedOperations;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
}
