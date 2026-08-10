package org.SprintForge.platform.communication.activity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.communication.dto.ActivityFeedResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("platformActivityFeedService")
@RequiredArgsConstructor
public class ActivityFeedService {

    private final ActivityFeedItemRepository activityRepository;

    @Transactional
    public ActivityFeedResponse recordActivity(String workspaceId, String projectId, String actorId, String entityType, String entityId, ActivityType activityType, String summary, String metadata) {
        ActivityFeedItem item = ActivityFeedItem.builder()
                .workspaceId(workspaceId)
                .projectId(projectId)
                .actorId(actorId)
                .entityType(entityType)
                .entityId(entityId)
                .activityType(activityType)
                .summary(summary)
                .metadata(metadata)
                .visibility("PUBLIC")
                .build();

        item = activityRepository.save(item);
        log.info("[ActivityFeedService] Recorded activity ID {} ({} on {} {})", item.getId(), activityType, entityType, entityId);
        return mapToResponse(item);
    }

    public ActivityFeedResponse mapToResponse(ActivityFeedItem item) {
        return ActivityFeedResponse.builder()
                .id(item.getId())
                .workspaceId(item.getWorkspaceId())
                .projectId(item.getProjectId())
                .actorId(item.getActorId())
                .entityType(item.getEntityType())
                .entityId(item.getEntityId())
                .activityType(item.getActivityType())
                .summary(item.getSummary())
                .metadata(item.getMetadata())
                .visibility(item.getVisibility())
                .createdAt(item.getCreatedAt())
                .build();
    }
}
