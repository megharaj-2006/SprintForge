package org.SprintForge.modules.workspace.automation.repository;

import org.SprintForge.modules.workspace.automation.entity.AutomationRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomationRuleRepository extends JpaRepository<AutomationRule, Long>, JpaSpecificationExecutor<AutomationRule> {
}