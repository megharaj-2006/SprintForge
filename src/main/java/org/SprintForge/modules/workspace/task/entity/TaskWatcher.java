package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_watchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskWatcher extends SoftDeleteEntity {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "watching_since")
    private LocalDateTime watchingSince = LocalDateTime.now();

    @Column(name = "notification_preference")
    private String notificationPreference = "ALL";
}
