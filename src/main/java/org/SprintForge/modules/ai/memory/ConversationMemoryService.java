package org.SprintForge.modules.ai.memory;

import org.SprintForge.modules.ai.entity.AIConversationMemory;
import org.SprintForge.modules.ai.entity.enums.MemoryScope;

import java.util.Optional;

public interface ConversationMemoryService {
    AIConversationMemory saveOrUpdateMemory(MemoryScope scope, String scopeId, String conversationId, String messagesJson, String summary);
    Optional<AIConversationMemory> getMemory(MemoryScope scope, String scopeId, String conversationId);
}
