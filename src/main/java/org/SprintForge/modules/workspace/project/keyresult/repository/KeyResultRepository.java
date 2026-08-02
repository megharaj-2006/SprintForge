package org.SprintForge.modules.workspace.project.keyresult.repository;

import org.SprintForge.modules.workspace.project.keyresult.entity.KeyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("strategicKeyResultRepository")
public interface KeyResultRepository extends JpaRepository<KeyResult, Long>, JpaSpecificationExecutor<KeyResult> {

    List<KeyResult> findByObjectiveIdAndIsDeletedFalse(Long objectiveId);

    long countByObjectiveIdAndIsDeletedFalse(Long objectiveId);
}
