package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.project.entity.Project;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "labels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Label extends SoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "description")
    private String description;

    @Column(name = "is_archived", nullable = false)
    private Boolean archived = false;

    @ManyToMany(mappedBy = "labels", fetch = FetchType.LAZY)
    private Set<Task> tasks = new HashSet<>();
}
