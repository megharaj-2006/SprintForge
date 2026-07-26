package org.SprintForge.modules.workspace.workspace.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceRoleRepository extends JpaRepository<WorkspaceRole, Long>, JpaSpecificationExecutor<WorkspaceRole> {

    List<WorkspaceRole> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);

    Optional<WorkspaceRole> findByWorkspaceIdAndNameAndIsDeletedFalse(Long workspaceId, String name);
}