package org.SprintForge.modules.integration.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConnectorExecutionCompletedEvent {
    private final String providerKey;
    private final String capabilityKey;
    private final long latencyMs;
}
