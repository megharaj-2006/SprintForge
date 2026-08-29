package org.SprintForge.platform.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.platform.communication.activity.ActivityType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityFeedResponse {
    private Long id;
    private String workspaceId;
    private String projectId;
    private String actorId;
    private String entityType;
    private String entityId;
    private ActivityType activityType;
    private String summary;
    private String metadata;
    private String visibility;
    private LocalDateTime createdAt;
}
