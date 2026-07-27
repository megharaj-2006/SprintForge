package org.SprintForge.modules.workspace.workspace.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
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
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long>, JpaSpecificationExecutor<WorkspaceMember> {

    Optional<WorkspaceMember> findByWorkspaceIdAndUserIdAndIsDeletedFalse(Long workspaceId, Long userId);

    boolean existsByWorkspaceIdAndUserIdAndIsDeletedFalse(Long workspaceId, Long userId);

    List<WorkspaceMember> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);

    List<WorkspaceMember> findByUserIdAndIsDeletedFalse(Long userId);

    long countByWorkspaceIdAndStatusAndIsDeletedFalse(Long workspaceId, WorkspaceMemberStatus status);

    @Query("SELECT wm FROM WorkspaceMember wm JOIN User u ON wm.userId = u.id " +
           "WHERE wm.workspaceId = :workspaceId AND wm.isDeleted = false " +
           "AND (:query IS NULL OR :query = '' " +
           "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<WorkspaceMember> searchWorkspaceMembers(@Param("workspaceId") Long workspaceId, @Param("query") String query, Pageable pageable);
}