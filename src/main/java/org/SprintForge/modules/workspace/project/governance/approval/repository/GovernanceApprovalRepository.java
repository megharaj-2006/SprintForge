package org.SprintForge.modules.workspace.project.governance.approval.repository;

import org.SprintForge.modules.workspace.project.governance.approval.entity.GovernanceApproval;
import org.SprintForge.modules.workspace.project.governance.approval.entity.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("governanceApprovalRepository")
public interface GovernanceApprovalRepository extends JpaRepository<GovernanceApproval, Long>, JpaSpecificationExecutor<GovernanceApproval> {

    List<GovernanceApproval> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<GovernanceApproval> findByStatusAndIsDeletedFalse(ApprovalStatus status);

    List<GovernanceApproval> findByProjectIdAndStatusAndIsDeletedFalse(Long projectId, ApprovalStatus status);

    long countByProjectIdAndStatusAndIsDeletedFalse(Long projectId, ApprovalStatus status);
}
