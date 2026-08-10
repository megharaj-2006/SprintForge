package org.SprintForge.modules.ai.repository;

import org.SprintForge.modules.ai.entity.AIConversationMemory;
import org.SprintForge.modules.ai.entity.enums.MemoryScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AIConversationMemoryRepository extends JpaRepository<AIConversationMemory, Long> {
    Optional<AIConversationMemory> findByMemoryScopeAndScopeIdAndConversationId(MemoryScope scope, String scopeId, String conversationId);
}
