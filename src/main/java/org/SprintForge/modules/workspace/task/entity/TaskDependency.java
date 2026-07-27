package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.task.entity.enums.TaskDependencyType;

@Entity
@Table(name = "task_dependencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDependency extends SoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predecessor_task_id", nullable = false)
    private Task predecessorTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "successor_task_id", nullable = false)
    private Task successorTask;

    @Enumerated(EnumType.STRING)
    @Column(name = "dependency_type")
    private TaskDependencyType type;
}
