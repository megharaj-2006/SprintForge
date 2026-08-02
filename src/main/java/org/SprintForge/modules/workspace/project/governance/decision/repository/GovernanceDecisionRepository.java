package org.SprintForge.modules.workspace.project.governance.decision.repository;

import org.SprintForge.modules.workspace.project.governance.decision.entity.GovernanceDecision;
import org.SprintForge.modules.workspace.project.governance.decision.entity.enums.DecisionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("governanceDecisionRepository")
public interface GovernanceDecisionRepository extends JpaRepository<GovernanceDecision, Long>, JpaSpecificationExecutor<GovernanceDecision> {

    List<GovernanceDecision> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<GovernanceDecision> findByProjectIdAndStatusAndIsDeletedFalse(Long projectId, DecisionStatus status);

    long countByProjectIdAndStatusAndIsDeletedFalse(Long projectId, DecisionStatus status);
}
