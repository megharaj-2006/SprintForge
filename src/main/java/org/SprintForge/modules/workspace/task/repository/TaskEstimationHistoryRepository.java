package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.TaskEstimationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskEstimationHistoryRepository extends JpaRepository<TaskEstimationHistory, Long>, JpaSpecificationExecutor<TaskEstimationHistory> {
}