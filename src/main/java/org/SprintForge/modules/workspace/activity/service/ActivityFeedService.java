package org.SprintForge.modules.workspace.activity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workspace.activity.entity.ActivityFeed;
import org.SprintForge.modules.workspace.activity.repository.ActivityFeedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityFeedService {

    private final ActivityFeedRepository activityFeedRepository;

    @Transactional
    public ActivityFeed recordActivity(Long userId, Long projectId, Long taskId, String activityType, String summary, String metadata) {
        ActivityFeed feed = new ActivityFeed();
        feed.setUserId(userId);
        feed.setProjectId(projectId);
        feed.setTaskId(taskId);
        feed.setActivityType(activityType);
        feed.setSummary(summary);
        feed.setMetadata(metadata);
        return activityFeedRepository.save(feed);
    }

    @Transactional(readOnly = true)
    public List<ActivityFeed> getGlobalActivity(int page, int size) {
        Page<ActivityFeed> feeds = activityFeedRepository.findByIsDeletedFalseOrderByCreatedAtDesc(PageRequest.of(page, size));
        return feeds.getContent();
    }

    @Transactional(readOnly = true)
    public List<ActivityFeed> getProjectActivity(Long projectId) {
        return activityFeedRepository.findByProjectIdAndIsDeletedFalseOrderByCreatedAtDesc(projectId);
    }

    @Transactional(readOnly = true)
    public List<ActivityFeed> getTaskActivity(Long taskId) {
        return activityFeedRepository.findByTaskIdAndIsDeletedFalseOrderByCreatedAtDesc(taskId);
    }

    @Transactional(readOnly = true)
    public List<ActivityFeed> getUserActivity(Long userId) {
        return activityFeedRepository.findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(userId);
    }
}
