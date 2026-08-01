package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.TaskTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long>, JpaSpecificationExecutor<TaskTemplate> {

    List<TaskTemplate> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);

    List<TaskTemplate> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<TaskTemplate> findByCreatedByUserIdAndIsDeletedFalse(Long createdByUserId);

    List<TaskTemplate> findByWorkspaceIdAndIsPublicTrueAndIsDeletedFalse(Long workspaceId);

    boolean existsByNameAndWorkspaceIdAndIsDeletedFalse(String name, Long workspaceId);

    @Query("SELECT t FROM TaskTemplate t WHERE t.workspaceId = :workspaceId AND t.isDeleted = false AND LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<TaskTemplate> searchTemplates(@Param("workspaceId") Long workspaceId, @Param("query") String query);

    @Query("SELECT t FROM TaskTemplate t WHERE t.workspaceId = :workspaceId AND t.isDeleted = false ORDER BY t.usageCount DESC")
    List<TaskTemplate> findPopularTemplates(@Param("workspaceId") Long workspaceId, Pageable pageable);

    @Modifying
    @Query("UPDATE TaskTemplate t SET t.usageCount = t.usageCount + 1 WHERE t.id = :id")
    void incrementUsageCount(@Param("id") Long id);
}