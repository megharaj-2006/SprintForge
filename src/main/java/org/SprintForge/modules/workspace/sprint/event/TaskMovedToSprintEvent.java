package org.SprintForge.modules.workspace.sprint.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TaskMovedToSprintEvent {
    private final List<Long> taskIds;
    private final Long targetSprintId;
    private final Long actorId;
}
