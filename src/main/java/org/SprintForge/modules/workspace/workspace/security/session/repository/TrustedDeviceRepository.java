package org.SprintForge.modules.workspace.workspace.security.session.repository;

import org.SprintForge.modules.workspace.workspace.security.session.entity.TrustedDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TrustedDeviceRepository extends JpaRepository<TrustedDevice, Long>, JpaSpecificationExecutor<TrustedDevice> {
    List<TrustedDevice> findByWorkspaceIdAndUserIdAndIsActiveTrue(Long workspaceId, Long userId);
    Optional<TrustedDevice> findByWorkspaceIdAndUserIdAndDeviceFingerprint(Long workspaceId, Long userId, String fingerprint);
    List<TrustedDevice> findByWorkspaceIdAndIsActiveTrue(Long workspaceId);
}
