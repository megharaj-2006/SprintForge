package org.SprintForge.modules.ai.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FallbackTriggeredEvent {
    private final String primaryProviderKey;
    private final String fallbackProviderKey;
    private final String reason;
}
