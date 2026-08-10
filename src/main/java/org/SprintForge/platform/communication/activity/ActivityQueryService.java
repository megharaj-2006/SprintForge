package org.SprintForge.platform.communication.activity;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.communication.dto.ActivityFeedResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("platformActivityQueryService")
@RequiredArgsConstructor
public class ActivityQueryService {

    private final ActivityFeedItemRepository activityRepository;
    private final ActivityFeedService activityFeedService;

    @Transactional(readOnly = true)
    public List<ActivityFeedResponse> getRecentWorkspaceActivity(String workspaceId, int page, int size) {
        return activityRepository
                .findByWorkspaceIdOrderByCreatedAtDesc(workspaceId, PageRequest.of(page, size))
                .stream()
                .map(activityFeedService::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ActivityFeedResponse> getRecentProjectActivity(String projectId, int page, int size) {
        return activityRepository
                .findByProjectIdOrderByCreatedAtDesc(projectId, PageRequest.of(page, size))
                .stream()
                .map(activityFeedService::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ActivityFeedResponse> getEntityActivity(String entityType, String entityId, int page, int size) {
        return activityRepository
                .findByEntityTypeAndEntityIdOrderByCreatedAtDesc(entityType, entityId, PageRequest.of(page, size))
                .stream()
                .map(activityFeedService::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ActivityFeedResponse> getUserActivity(String actorId, int page, int size) {
        return activityRepository
                .findByActorIdOrderByCreatedAtDesc(actorId, PageRequest.of(page, size))
                .stream()
                .map(activityFeedService::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ActivityFeedResponse> getAllGlobalActivity(int page, int size) {
        return activityRepository
                .findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
                .stream()
                .map(activityFeedService::mapToResponse)
                .toList();
    }
}
