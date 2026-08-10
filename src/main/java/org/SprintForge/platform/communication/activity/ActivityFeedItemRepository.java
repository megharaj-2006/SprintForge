package org.SprintForge.platform.communication.activity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("platformActivityFeedItemRepository")
public interface ActivityFeedItemRepository extends JpaRepository<ActivityFeedItem, Long> {

    List<ActivityFeedItem> findByWorkspaceIdOrderByCreatedAtDesc(String workspaceId, Pageable pageable);

    List<ActivityFeedItem> findByProjectIdOrderByCreatedAtDesc(String projectId, Pageable pageable);

    List<ActivityFeedItem> findByEntityTypeAndEntityIdOrderByCreatedAtDesc(String entityType, String entityId, Pageable pageable);

    List<ActivityFeedItem> findByActorIdOrderByCreatedAtDesc(String actorId, Pageable pageable);

    List<ActivityFeedItem> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
