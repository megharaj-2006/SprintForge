package org.SprintForge.modules.workspace.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskNotification extends SoftDeleteEntity {

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "workspace_id")
    private Long workspaceId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "actor_id")
    private Long actorId;

    @Column(name = "type")
    private String type; // TASK_CREATED, TASK_ASSIGNED, MENTION, COMMENT_ADDED, SPRINT_STARTED, etc.

    @Column(name = "priority")
    private String priority = "NORMAL"; // LOW, NORMAL, HIGH, URGENT

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "action_url")
    private String actionUrl;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "read_at")
    private LocalDateTime readAt;
}
