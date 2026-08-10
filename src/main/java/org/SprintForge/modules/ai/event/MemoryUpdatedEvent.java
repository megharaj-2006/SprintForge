package org.SprintForge.modules.ai.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemoryUpdatedEvent {
    private final String scope;
    private final String scopeId;
    private final String conversationId;
}
