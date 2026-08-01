package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "task_permission_overrides")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskPermissionOverride extends SoftDeleteEntity {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "permission", nullable = false)
    private String permission; // VIEW_TASK, EDIT_TASK, DELETE_TASK, ASSIGN_TASK, COMMENT_TASK, etc.

    @Column(name = "allowed", nullable = false)
    private Boolean allowed = true;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;
}
