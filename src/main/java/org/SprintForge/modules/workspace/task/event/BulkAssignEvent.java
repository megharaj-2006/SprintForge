package org.SprintForge.modules.workspace.task.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BulkAssignEvent {
    private final List<Long> taskIds;
    private final Long assigneeId;
    private final Long actorId;
}
