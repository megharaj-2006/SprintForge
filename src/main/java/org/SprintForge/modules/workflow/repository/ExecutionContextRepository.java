package org.SprintForge.modules.workflow.repository;

import org.SprintForge.modules.workflow.entity.ExecutionContextEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExecutionContextRepository extends JpaRepository<ExecutionContextEntity, Long> {
    Optional<ExecutionContextEntity> findByWorkflowExecutionId(Long workflowExecutionId);
}
