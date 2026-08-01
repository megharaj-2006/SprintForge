package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "trash_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrashRecord extends SoftDeleteEntity {

    @Column(name = "entity_type", nullable = false)
    private String entityType; // TASK, PROJECT, SPRINT, EPIC, VIEW

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "deleted_by_user_id")
    private Long deletedByUserId;

    @Column(name = "deleted_at", nullable = false)
    private LocalDateTime deletedAt;

    @Column(name = "scheduled_purge_at")
    private LocalDateTime scheduledPurgeAt;

    @Column(name = "reason")
    private String reason;
}
