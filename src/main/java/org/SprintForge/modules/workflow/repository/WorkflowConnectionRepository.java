package org.SprintForge.modules.workflow.repository;

import org.SprintForge.modules.workflow.entity.WorkflowConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowConnectionRepository extends JpaRepository<WorkflowConnection, Long> {
    List<WorkflowConnection> findByWorkflowVersionId(Long workflowVersionId);
    List<WorkflowConnection> findBySourceNodeId(Long sourceNodeId);
}
