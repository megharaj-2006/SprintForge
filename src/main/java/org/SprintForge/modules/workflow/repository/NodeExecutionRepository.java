package org.SprintForge.modules.workflow.repository;

import org.SprintForge.modules.workflow.entity.NodeExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeExecutionRepository extends JpaRepository<NodeExecution, Long> {
    List<NodeExecution> findByWorkflowExecutionId(Long workflowExecutionId);
}
