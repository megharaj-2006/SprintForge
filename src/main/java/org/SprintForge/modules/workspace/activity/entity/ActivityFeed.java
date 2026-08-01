package org.SprintForge.modules.workspace.activity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "activity_feeds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityFeed extends SoftDeleteEntity {

    @Column(name = "workspace_id")
    private Long workspaceId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "activity_type", nullable = false)
    private String activityType; // TASK_CREATED, COMMENT_ADDED, ATTACHMENT_UPLOADED, PRIORITY_CHANGED, CHECKLIST_COMPLETED, SPRINT_STARTED, etc.

    @Column(name = "summary", nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata; // JSON string for extra contextual details

    @Column(name = "visibility")
    private String visibility = "PUBLIC";
}
