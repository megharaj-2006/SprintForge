package org.SprintForge.modules.workspace.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "project_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRole extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "permissions", columnDefinition = "TEXT")
    private String permissions;
}
