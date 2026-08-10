package org.SprintForge.modules.workflow.governance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkflowPolicyRepository extends JpaRepository<WorkflowPolicy, Long> {
    Optional<WorkflowPolicy> findByWorkspaceIdAndEnabledTrue(Long workspaceId);
}
