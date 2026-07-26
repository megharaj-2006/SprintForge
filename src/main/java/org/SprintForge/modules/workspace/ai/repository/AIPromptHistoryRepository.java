package org.SprintForge.modules.workspace.ai.repository;

import org.SprintForge.modules.workspace.ai.entity.AIPromptHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AIPromptHistoryRepository extends JpaRepository<AIPromptHistory, Long>, JpaSpecificationExecutor<AIPromptHistory> {
}