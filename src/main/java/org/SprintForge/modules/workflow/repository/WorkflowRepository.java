package org.SprintForge.modules.workflow.repository;

import org.SprintForge.modules.workflow.entity.Workflow;
import org.SprintForge.modules.workflow.entity.enums.WorkflowStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
    Page<Workflow> findByWorkspaceIdAndArchivedFalse(Long workspaceId, Pageable pageable);
    Page<Workflow> findByArchivedFalse(Pageable pageable);
    List<Workflow> findByStatusAndEnabledTrue(WorkflowStatus status);
    Optional<Workflow> findByIdAndArchivedFalse(Long id);
}
