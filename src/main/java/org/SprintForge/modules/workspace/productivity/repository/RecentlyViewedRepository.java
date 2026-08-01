package org.SprintForge.modules.workspace.productivity.repository;

import org.SprintForge.modules.workspace.productivity.entity.RecentlyViewed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecentlyViewedRepository extends JpaRepository<RecentlyViewed, Long> {

    List<RecentlyViewed> findByUserIdAndIsDeletedFalseOrderByLastViewedAtDesc(Long userId);

    Optional<RecentlyViewed> findByUserIdAndEntityTypeAndEntityIdAndIsDeletedFalse(Long userId, String entityType, Long entityId);
}
