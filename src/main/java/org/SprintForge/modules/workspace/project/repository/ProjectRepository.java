package org.SprintForge.modules.workspace.project.repository;

import org.SprintForge.modules.workspace.project.entity.Project;
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
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    Optional<Project> findByWorkspaceIdAndProjectKeyAndIsDeletedFalse(Long workspaceId, String projectKey);

    boolean existsByWorkspaceIdAndNameAndIsDeletedFalse(Long workspaceId, String name);

    boolean existsByWorkspaceIdAndProjectKeyAndIsDeletedFalse(Long workspaceId, String projectKey);

    List<Project> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);

    Page<Project> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId, Pageable pageable);

    List<Project> findByWorkspaceIdAndIsArchivedFalseAndIsDeletedFalse(Long workspaceId);

    Page<Project> findByWorkspaceIdAndIsArchivedFalseAndIsDeletedFalse(Long workspaceId, Pageable pageable);

    List<Project> findByWorkspaceIdAndIsArchivedTrueAndIsDeletedFalse(Long workspaceId);

    Page<Project> findByWorkspaceIdAndIsArchivedTrueAndIsDeletedFalse(Long workspaceId, Pageable pageable);

    List<Project> findByOwnerIdAndIsDeletedFalse(Long ownerId);

    Page<Project> findByOwnerIdAndIsDeletedFalse(Long ownerId, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.workspaceId = :workspaceId AND p.isDeleted = false AND (p.ownerId = :userId OR p.id IN (SELECT pm.projectId FROM ProjectMember pm, WorkspaceMember wm WHERE pm.workspaceMemberId = wm.id AND wm.userId = :userId AND pm.isDeleted = false))")
    Page<Project> findProjectsByWorkspaceAndUser(@Param("workspaceId") Long workspaceId, @Param("userId") Long userId, Pageable pageable);

    long countByWorkspaceIdAndIsDeletedFalse(Long workspaceId);
}