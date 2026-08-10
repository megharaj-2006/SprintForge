package org.SprintForge.modules.ai.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CapabilityRegisteredEvent {
    private final String capabilityKey;
    private final String category;
}
