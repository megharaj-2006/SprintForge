package org.SprintForge.modules.workspace.sprint.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SprintArchivedEvent {
    private Long sprintId;
    private Long actorId;
    private LocalDateTime timestamp;

    public SprintArchivedEvent(Long sprintId, Long actorId) {
        this.sprintId = sprintId;
        this.actorId = actorId;
        this.timestamp = LocalDateTime.now();
    }
}
