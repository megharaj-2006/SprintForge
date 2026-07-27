package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.entity.Project;

@Entity
@Table(name = "task_labels",
        uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "name"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskLabel extends SoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "archived", nullable = false)
    private boolean archived = false;
}
