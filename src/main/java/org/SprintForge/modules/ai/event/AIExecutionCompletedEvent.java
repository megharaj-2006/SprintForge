package org.SprintForge.modules.ai.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AIExecutionCompletedEvent {
    private final String capabilityKey;
    private final String providerKey;
    private final long latencyMs;
    private final int totalTokens;
}
