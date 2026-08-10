package org.SprintForge.modules.workflow.repository;

import org.SprintForge.modules.workflow.entity.WorkflowVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowVersionRepository extends JpaRepository<WorkflowVersion, Long> {
    List<WorkflowVersion> findByWorkflowIdOrderByVersionNumberDesc(Long workflowId);
    Optional<WorkflowVersion> findByWorkflowIdAndVersionNumber(Long workflowId, Integer versionNumber);

    @Query("SELECT MAX(wv.versionNumber) FROM WorkflowVersion wv WHERE wv.workflow.id = :workflowId")
    Optional<Integer> findMaxVersionNumberByWorkflowId(@Param("workflowId") Long workflowId);
}
