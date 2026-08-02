package org.SprintForge.modules.workspace.project.objective.repository;

import org.SprintForge.modules.workspace.project.objective.entity.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("strategicObjectiveRepository")
public interface ObjectiveRepository extends JpaRepository<Objective, Long>, JpaSpecificationExecutor<Objective> {

    List<Objective> findByGoalIdAndIsDeletedFalse(Long goalId);

    long countByGoalIdAndIsDeletedFalse(Long goalId);
}
