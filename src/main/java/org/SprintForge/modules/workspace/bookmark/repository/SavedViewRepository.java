package org.SprintForge.modules.workspace.bookmark.repository;

import org.SprintForge.modules.workspace.bookmark.entity.SavedView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SavedViewRepository extends JpaRepository<SavedView, Long>, JpaSpecificationExecutor<SavedView> {

    List<SavedView> findByUserIdAndIsDeletedFalse(Long userId);

    List<SavedView> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<SavedView> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);

    @Query("SELECT v FROM SavedView v WHERE v.projectId = :projectId AND v.isDeleted = false AND (v.userId = :userId OR v.isShared = true OR v.visibility IN ('WORKSPACE', 'PROJECT'))")
    List<SavedView> findAccessibleViewsForProject(@Param("projectId") Long projectId, @Param("userId") Long userId);

    Optional<SavedView> findByUserIdAndProjectIdAndIsDefaultTrueAndIsDeletedFalse(Long userId, Long projectId);

    List<SavedView> findByUserIdAndIsFavoriteTrueAndIsDeletedFalse(Long userId);

    boolean existsByNameAndProjectIdAndUserIdAndIsDeletedFalse(String name, Long projectId, Long userId);
}