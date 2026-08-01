package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.TaskEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskEstimateRepository extends JpaRepository<TaskEstimate, Long> {

    List<TaskEstimate> findByTaskIdOrderByCreatedAtDesc(Long taskId);
}
