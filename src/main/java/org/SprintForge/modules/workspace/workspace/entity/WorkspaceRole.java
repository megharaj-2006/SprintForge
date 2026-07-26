package org.SprintForge.modules.workspace.workspace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "workspace_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceRole extends SoftDeleteEntity {

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "is_system_role")
    private Boolean isSystemRole = false;

    @Column(name = "is_default_role")
    private Boolean isDefaultRole = false;

    @Column(name = "permissions", columnDefinition = "TEXT")
    private String permissions;
}

