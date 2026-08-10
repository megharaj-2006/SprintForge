package org.SprintForge.modules.workflow.repository;

import org.SprintForge.modules.workflow.entity.WorkflowVariable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowVariableRepository extends JpaRepository<WorkflowVariable, Long> {
    List<WorkflowVariable> findByWorkflowVersionId(Long workflowVersionId);
    Optional<WorkflowVariable> findByWorkflowVersionIdAndName(Long workflowVersionId, String name);
}
