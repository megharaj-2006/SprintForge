package org.SprintForge.modules.workspace.workspace.repository;

import org.SprintForge.modules.workspace.workspace.entity.Workspace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long>, JpaSpecificationExecutor<Workspace> {

    Optional<Workspace> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Workspace> findByOwnerIdAndIsDeletedFalse(Long ownerId);

    @Query("SELECT w FROM Workspace w WHERE w.isDeleted = false AND (w.ownerId = :userId OR w.id IN (SELECT wm.workspaceId FROM WorkspaceMember wm WHERE wm.userId = :userId AND wm.status = 'ACTIVE'))")
    Page<Workspace> findAllWorkspacesForUser(@Param("userId") Long userId, Pageable pageable);

    long countByOwnerIdAndIsDeletedFalse(Long ownerId);
}