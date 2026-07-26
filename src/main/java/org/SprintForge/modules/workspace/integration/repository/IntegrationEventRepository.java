package org.SprintForge.modules.workspace.integration.repository;

import org.SprintForge.modules.workspace.integration.entity.IntegrationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegrationEventRepository extends JpaRepository<IntegrationEvent, Long>, JpaSpecificationExecutor<IntegrationEvent> {
}