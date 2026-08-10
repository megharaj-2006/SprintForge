package org.SprintForge.modules.integration.repository;

import org.SprintForge.modules.integration.entity.IntegrationConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntegrationConnectionRepository extends JpaRepository<IntegrationConnection, Long> {
    List<IntegrationConnection> findByWorkspaceId(Long workspaceId);
}
