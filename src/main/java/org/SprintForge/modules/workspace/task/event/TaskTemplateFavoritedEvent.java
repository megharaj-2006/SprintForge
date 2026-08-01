package org.SprintForge.modules.workspace.task.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskTemplateFavoritedEvent {
    private final Long templateId;
    private final Long userId;
    private final boolean favorited;
}
