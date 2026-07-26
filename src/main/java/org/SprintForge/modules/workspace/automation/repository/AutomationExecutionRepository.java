package org.SprintForge.modules.workspace.automation.repository;

import org.SprintForge.modules.workspace.automation.entity.AutomationExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomationExecutionRepository extends JpaRepository<AutomationExecution, Long>, JpaSpecificationExecutor<AutomationExecution> {
}