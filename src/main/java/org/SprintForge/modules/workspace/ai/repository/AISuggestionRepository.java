package org.SprintForge.modules.workspace.ai.repository;

import org.SprintForge.modules.workspace.ai.entity.AISuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AISuggestionRepository extends JpaRepository<AISuggestion, Long>, JpaSpecificationExecutor<AISuggestion> {
}