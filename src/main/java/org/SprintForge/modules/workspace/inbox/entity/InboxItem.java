package org.SprintForge.modules.workspace.inbox.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "inbox_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InboxItem extends SoftDeleteEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "type", nullable = false)
    private String type; // ASSIGNMENT, MENTION, COMMENT, APPROVAL, TASK_UPDATE, SPRINT_UPDATE

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "is_archived")
    private Boolean isArchived = false;

    @Column(name = "snoozed_until")
    private LocalDateTime snoozedUntil;
}
