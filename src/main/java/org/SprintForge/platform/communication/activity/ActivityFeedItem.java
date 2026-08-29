package org.SprintForge.platform.communication.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "PlatformActivityFeedItem")
@Table(name = "communication_activity_feed")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityFeedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workspace_id", length = 100)
    private String workspaceId;

    @Column(name = "project_id", length = 100)
    private String projectId;

    @Column(name = "actor_id", nullable = false, length = 100)
    private String actorId;

    @Column(name = "entity_type", nullable = false, length = 100)
    private String entityType;

    @Column(name = "entity_id", nullable = false, length = 100)
    private String entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false, length = 50)
    private ActivityType activityType;

    @Column(name = "summary", nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "visibility", nullable = false, length = 30)
    private String visibility;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (visibility == null) {
            visibility = "PUBLIC";
        }
    }
}
