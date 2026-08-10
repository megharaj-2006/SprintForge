package org.SprintForge.modules.ai.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PromptCreatedEvent {
    private final String templateKey;
    private final String name;
}
