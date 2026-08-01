package org.SprintForge.modules.workspace.sprint.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SprintStartedEvent {
    private Long sprintId;
    private Long projectId;
    private Long actorId;
    private LocalDateTime timestamp;

    public SprintStartedEvent(Long sprintId, Long projectId, Long actorId) {
        this.sprintId = sprintId;
        this.projectId = projectId;
        this.actorId = actorId;
        this.timestamp = LocalDateTime.now();
    }

    public SprintStartedEvent(Long sprintId, Long actorId, LocalDateTime timestamp) {
        this.sprintId = sprintId;
        this.actorId = actorId;
        this.timestamp = timestamp;
    }
}
