package org.SprintForge.modules.workspace.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectDependencyType;

@Entity
@Table(name = "project_dependencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDependency extends SoftDeleteEntity {

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "depends_on_project_id", nullable = false)
    private Long dependsOnProjectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "dependency_type")
    private ProjectDependencyType dependencyType = ProjectDependencyType.RELATES_TO;
}

