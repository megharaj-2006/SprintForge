package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;
import org.SprintForge.modules.workspace.task.entity.enums.TaskRelationType;

@Entity
@Table(name = "task_relations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRelation extends SoftDeleteEntity {

    @Column(name = "source_task_id", nullable = false)
    private Long sourceTaskId;

    @Column(name = "target_task_id", nullable = false)
    private Long targetTaskId;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation_type")
    private TaskRelationType relationType = TaskRelationType.RELATED;
}

