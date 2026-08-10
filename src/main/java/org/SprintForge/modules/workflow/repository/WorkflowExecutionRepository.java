package org.SprintForge.modules.workflow.repository;

import org.SprintForge.modules.workflow.entity.WorkflowExecution;
import org.SprintForge.modules.workflow.entity.enums.ExecutionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowExecutionRepository extends JpaRepository<WorkflowExecution, Long> {
    Page<WorkflowExecution> findByWorkflowVersionWorkflowId(Long workflowId, Pageable pageable);
    List<WorkflowExecution> findByWorkflowVersionWorkflowId(Long workflowId);

    @Query("SELECT COUNT(e) FROM WorkflowExecution e WHERE e.workflowVersion.workflow.id = :workflowId")
    long countByWorkflowId(@Param("workflowId") Long workflowId);

    @Query("SELECT COUNT(e) FROM WorkflowExecution e WHERE e.workflowVersion.workflow.id = :workflowId AND e.status = :status")
    long countByWorkflowIdAndStatus(@Param("workflowId") Long workflowId, @Param("status") ExecutionStatus status);

    @Query("SELECT AVG(e.duration) FROM WorkflowExecution e WHERE e.workflowVersion.workflow.id = :workflowId AND e.duration IS NOT NULL")
    Double findAverageDurationByWorkflowId(@Param("workflowId") Long workflowId);

    @Query("SELECT e.workflowVersion.workflow.id, COUNT(e) FROM WorkflowExecution e GROUP BY e.workflowVersion.workflow.id ORDER BY COUNT(e) DESC")
    List<Object[]> findMostExecutedWorkflows(Pageable pageable);
}
