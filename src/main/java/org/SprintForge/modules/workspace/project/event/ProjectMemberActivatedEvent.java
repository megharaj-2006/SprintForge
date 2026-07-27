package org.SprintForge.modules.workspace.project.event;

import java.time.LocalDateTime;

public record ProjectMemberActivatedEvent(
        Long projectId,
        Long workspaceMemberId,
        Long actorId,
        LocalDateTime timestamp
) {}
