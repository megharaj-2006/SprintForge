package org.SprintForge.modules.workspace.goal.repository;

import org.SprintForge.modules.workspace.goal.entity.ProjectGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectGoalRepository extends JpaRepository<ProjectGoal, Long>, JpaSpecificationExecutor<ProjectGoal> {
}