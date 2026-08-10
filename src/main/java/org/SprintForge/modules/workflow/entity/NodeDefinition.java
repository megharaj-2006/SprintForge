package org.SprintForge.modules.workflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.AuditableEntity;

@Entity
@Table(name = "node_definitions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NodeDefinition extends AuditableEntity {

    @Column(name = "node_key", nullable = false, unique = true)
    private String nodeKey;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "configuration_schema", columnDefinition = "TEXT")
    private String configurationSchema;

    @Column(name = "output_schema", columnDefinition = "TEXT")
    private String outputSchema;

    @Column(name = "definition_version", nullable = false)
    private String definitionVersion = "1.0";

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
}
