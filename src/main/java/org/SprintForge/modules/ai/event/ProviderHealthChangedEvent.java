package org.SprintForge.modules.ai.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProviderHealthChangedEvent {
    private final String providerKey;
    private final boolean healthy;
}
