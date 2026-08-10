package org.SprintForge.modules.workspace.workspace.security.session.repository;

import org.SprintForge.modules.workspace.workspace.security.session.entity.WorkspaceSession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface WorkspaceSessionRepository extends JpaRepository<WorkspaceSession, Long>, JpaSpecificationExecutor<WorkspaceSession> {
    List<WorkspaceSession> findByWorkspaceIdAndIsActiveTrue(Long workspaceId);
    List<WorkspaceSession> findByUserIdAndWorkspaceIdAndIsActiveTrue(Long userId, Long workspaceId);
    Optional<WorkspaceSession> findBySessionToken(String token);
    List<WorkspaceSession> findByUserIdAndIsActiveTrue(Long userId, Pageable pageable);
}
