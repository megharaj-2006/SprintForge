package org.SprintForge.modules.workspace.productivity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "recently_viewed_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecentlyViewed extends SoftDeleteEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "entity_type", nullable = false)
    private String entityType; // TASK, PROJECT, SPRINT, EPIC, VIEW

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "title")
    private String title;

    @Column(name = "last_viewed_at", nullable = false)
    private LocalDateTime lastViewedAt;
}
