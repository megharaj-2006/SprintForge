package org.SprintForge.modules.ai.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AIProviderRegisteredEvent {
    private final String providerKey;
    private final String displayName;
}
