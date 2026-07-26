package org.SprintForge.modules.workspace.workspace.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspacePreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkspacePreferenceRepository extends JpaRepository<WorkspacePreference, Long>, JpaSpecificationExecutor<WorkspacePreference> {

    Optional<WorkspacePreference> findByWorkspaceIdAndUserIdAndIsDeletedFalse(Long workspaceId, Long userId);
}
