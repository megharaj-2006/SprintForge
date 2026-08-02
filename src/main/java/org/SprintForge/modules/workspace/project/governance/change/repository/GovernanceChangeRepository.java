package org.SprintForge.modules.workspace.project.governance.change.repository;

import org.SprintForge.modules.workspace.project.governance.change.entity.GovernanceChange;
import org.SprintForge.modules.workspace.project.governance.change.entity.enums.ChangeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("governanceChangeRepository")
public interface GovernanceChangeRepository extends JpaRepository<GovernanceChange, Long>, JpaSpecificationExecutor<GovernanceChange> {

    List<GovernanceChange> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<GovernanceChange> findByProjectIdAndStatusAndIsDeletedFalse(Long projectId, ChangeStatus status);

    long countByProjectIdAndIsDeletedFalse(Long projectId);
}
