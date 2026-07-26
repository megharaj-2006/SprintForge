package org.SprintForge.modules.workspace.integration.repository;

import org.SprintForge.modules.workspace.integration.entity.Integration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegrationRepository extends JpaRepository<Integration, Long>, JpaSpecificationExecutor<Integration> {
}