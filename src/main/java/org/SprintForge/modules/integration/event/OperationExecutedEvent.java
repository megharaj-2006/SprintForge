package org.SprintForge.modules.integration.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OperationExecutedEvent {
    private final String providerKey;
    private final String operationKey;
    private final long durationMs;
}
