package org.SprintForge.modules.integration.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IntegrationProviderRegisteredEvent {
    private final String providerKey;
    private final String displayName;
}
