package org.SprintForge.modules.ai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;
import org.SprintForge.modules.ai.entity.enums.MemoryScope;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_conversation_memories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AIConversationMemory extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "memory_scope", nullable = false)
    private MemoryScope memoryScope = MemoryScope.WORKFLOW;

    @Column(name = "scope_id", nullable = false)
    private String scopeId;

    @Column(name = "conversation_id", nullable = false)
    private String conversationId;

    @Column(name = "messages_json", columnDefinition = "TEXT")
    private String messagesJson;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "token_count")
    private Integer tokenCount = 0;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
