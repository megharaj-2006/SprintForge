package org.SprintForge.modules.integration.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConnectionCreatedEvent {
    private final Long connectionId;
    private final String providerKey;
    private final Long workspaceId;
}
