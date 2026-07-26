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

    Optional<Project> findByWorkspaceIdAndProjectKey(Long workspaceId, String projectKey);

    boolean existsByWorkspaceIdAndProjectKey(Long workspaceId, String projectKey);

    List<Project> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);

    Page<Project> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.workspaceId = :workspaceId AND p.isDeleted = false AND (p.ownerId = :userId OR p.id IN (SELECT pm.projectId FROM ProjectMember pm WHERE pm.userId = :userId))")
    Page<Project> findProjectsByWorkspaceAndUser(@Param("workspaceId") Long workspaceId, @Param("userId") Long userId, Pageable pageable);

    long countByWorkspaceIdAndIsDeletedFalse(Long workspaceId);
}