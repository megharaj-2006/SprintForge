package org.SprintForge.modules.ai.repository;

import org.SprintForge.modules.ai.entity.AIExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIExecutionRepository extends JpaRepository<AIExecution, Long> {
}
