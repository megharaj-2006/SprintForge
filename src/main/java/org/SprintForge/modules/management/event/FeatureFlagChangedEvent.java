package org.SprintForge.modules.management.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeatureFlagChangedEvent {
    private final String flagKey;
    private final boolean enabled;
}
