package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_template_checklists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskTemplateChecklist extends SoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_template_id", nullable = false)
    private TaskTemplate taskTemplate;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "position")
    private Integer position;

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskTemplateChecklistItem> items = new ArrayList<>();
}
