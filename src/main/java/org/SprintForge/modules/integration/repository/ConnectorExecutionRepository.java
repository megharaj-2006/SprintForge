package org.SprintForge.modules.integration.repository;

import org.SprintForge.modules.integration.entity.ConnectorExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectorExecutionRepository extends JpaRepository<ConnectorExecution, Long> {
}
