package org.SprintForge.modules.workspace.activity.repository;

import org.SprintForge.modules.workspace.activity.entity.ActivityFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityFeedRepository extends JpaRepository<ActivityFeed, Long> {

    List<ActivityFeed> findByProjectIdAndIsDeletedFalseOrderByCreatedAtDesc(Long projectId);

    List<ActivityFeed> findByTaskIdAndIsDeletedFalseOrderByCreatedAtDesc(Long taskId);

    List<ActivityFeed> findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(Long userId);

    Page<ActivityFeed> findByIsDeletedFalseOrderByCreatedAtDesc(Pageable pageable);
}
