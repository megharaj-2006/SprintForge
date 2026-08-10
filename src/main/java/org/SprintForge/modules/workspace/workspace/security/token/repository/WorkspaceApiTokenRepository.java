package org.SprintForge.modules.workspace.workspace.security.token.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceApiToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkspaceApiTokenRepository extends JpaRepository<WorkspaceApiToken, Long>, JpaSpecificationExecutor<WorkspaceApiToken> {
    List<WorkspaceApiToken> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);
    List<WorkspaceApiToken> findByWorkspaceIdAndIsRevokedFalseAndIsDeletedFalse(Long workspaceId);
    Optional<WorkspaceApiToken> findByTokenHashAndIsDeletedFalse(String tokenHash);
    List<WorkspaceApiToken> findByExpiresAtBeforeAndIsRevokedFalseAndIsDeletedFalse(LocalDateTime now);
}
