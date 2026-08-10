package org.SprintForge.modules.ai.memory;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.entity.AIConversationMemory;
import org.SprintForge.modules.ai.entity.enums.MemoryScope;
import org.SprintForge.modules.ai.repository.AIConversationMemoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationMemoryServiceImpl implements ConversationMemoryService {

    private final AIConversationMemoryRepository repository;

    @Override
    @Transactional
    public AIConversationMemory saveOrUpdateMemory(MemoryScope scope, String scopeId, String conversationId, String messagesJson, String summary) {
        Optional<AIConversationMemory> existing = repository.findByMemoryScopeAndScopeIdAndConversationId(scope, scopeId, conversationId);
        AIConversationMemory memory = existing.orElseGet(AIConversationMemory::new);

        memory.setMemoryScope(scope);
        memory.setScopeId(scopeId);
        memory.setConversationId(conversationId);
        memory.setMessagesJson(messagesJson);
        memory.setSummary(summary);
        memory.setUpdatedAt(LocalDateTime.now());

        return repository.save(memory);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AIConversationMemory> getMemory(MemoryScope scope, String scopeId, String conversationId) {
        return repository.findByMemoryScopeAndScopeIdAndConversationId(scope, scopeId, conversationId);
    }
}
