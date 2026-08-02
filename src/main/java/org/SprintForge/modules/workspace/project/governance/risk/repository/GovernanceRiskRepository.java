package org.SprintForge.modules.workspace.project.governance.risk.repository;

import org.SprintForge.modules.workspace.project.governance.risk.entity.GovernanceRisk;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("governanceRiskRepository")
public interface GovernanceRiskRepository extends JpaRepository<GovernanceRisk, Long>, JpaSpecificationExecutor<GovernanceRisk> {

    List<GovernanceRisk> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<GovernanceRisk> findByProjectIdAndStatusAndIsDeletedFalse(Long projectId, RiskStatus status);

    List<GovernanceRisk> findByProjectIdAndSeverityAndIsDeletedFalse(Long projectId, String severity);

    long countByProjectIdAndStatusAndIsDeletedFalse(Long projectId, RiskStatus status);

    long countByProjectIdAndSeverityAndIsDeletedFalse(Long projectId, String severity);
}
