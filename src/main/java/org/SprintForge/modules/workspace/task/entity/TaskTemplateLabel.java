package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "task_template_labels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskTemplateLabel extends SoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_template_id", nullable = false)
    private TaskTemplate taskTemplate;

    @Column(name = "label_id", nullable = false)
    private Long labelId;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;
}
