package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "advanced_task_relationships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedTaskRelationship extends SoftDeleteEntity {

    @Column(name = "source_task_id", nullable = false)
    private Long sourceTaskId;

    @Column(name = "target_task_id", nullable = false)
    private Long targetTaskId;

    @Column(name = "relationship_type", nullable = false)
    private String relationshipType; // BLOCKS, BLOCKED_BY, DUPLICATE, RELATES_TO, PARENT, CHILD, SPLIT_FROM, MERGED_INTO, COPIED_FROM

    @Column(name = "created_by_user_id")
    private Long createdByUserId;
}
