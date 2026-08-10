package org.SprintForge.modules.workspace.workspace.branding.repository;

import org.SprintForge.modules.workspace.workspace.branding.entity.WorkspaceBranding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkspaceBrandingRepository extends JpaRepository<WorkspaceBranding, Long>, JpaSpecificationExecutor<WorkspaceBranding> {
    Optional<WorkspaceBranding> findByWorkspaceId(Long workspaceId);
    boolean existsByWorkspaceId(Long workspaceId);
}
