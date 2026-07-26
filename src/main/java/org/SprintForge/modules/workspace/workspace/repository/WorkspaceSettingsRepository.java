package org.SprintForge.modules.workspace.workspace.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkspaceSettingsRepository extends JpaRepository<WorkspaceSettings, Long>, JpaSpecificationExecutor<WorkspaceSettings> {

    Optional<WorkspaceSettings> findByWorkspaceId(Long workspaceId);

    Optional<WorkspaceSettings> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);
}