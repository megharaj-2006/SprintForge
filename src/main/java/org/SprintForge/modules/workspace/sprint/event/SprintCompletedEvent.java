package org.SprintForge.modules.workspace.sprint.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SprintCompletedEvent {
    private Long sprintId;
    private Long projectId;
    private int completedTasks;
    private int remainingTasksMoved;
    private Long actorId;
    private LocalDateTime timestamp;

    public SprintCompletedEvent(Long sprintId, Long projectId, int completedTasks, int remainingTasksMoved, Long actorId) {
        this.sprintId = sprintId;
        this.projectId = projectId;
        this.completedTasks = completedTasks;
        this.remainingTasksMoved = remainingTasksMoved;
        this.actorId = actorId;
        this.timestamp = LocalDateTime.now();
    }

    public SprintCompletedEvent(Long sprintId, Long actorId, LocalDateTime timestamp) {
        this.sprintId = sprintId;
        this.actorId = actorId;
        this.timestamp = timestamp;
    }
}
