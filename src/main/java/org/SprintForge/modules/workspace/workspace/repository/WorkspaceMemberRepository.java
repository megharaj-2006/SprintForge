package org.SprintForge.modules.workspace.workspace.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
}