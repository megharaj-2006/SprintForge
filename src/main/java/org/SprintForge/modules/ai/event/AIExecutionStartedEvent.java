package org.SprintForge.modules.ai.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AIExecutionStartedEvent {
    private final String capabilityKey;
    private final String providerKey;
    private final String policyKey;
}
