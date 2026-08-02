package org.SprintForge.modules.workspace.project.goal.repository;

import org.SprintForge.modules.workspace.project.goal.entity.Goal;
import org.SprintForge.modules.workspace.project.goal.entity.enums.GoalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("strategicGoalRepository")
public interface GoalRepository extends JpaRepository<Goal, Long>, JpaSpecificationExecutor<Goal> {

    List<Goal> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<Goal> findByProjectIdAndStatusAndIsDeletedFalse(Long projectId, GoalStatus status);

    List<Goal> findByProjectIdAndIsArchivedFalseAndIsDeletedFalse(Long projectId);
}
