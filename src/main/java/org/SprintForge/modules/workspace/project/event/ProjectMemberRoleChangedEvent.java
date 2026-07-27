package org.SprintForge.modules.workspace.project.event;

import java.time.LocalDateTime;

public record ProjectMemberRoleChangedEvent(
        Long projectId,
        Long workspaceMemberId,
        Long oldRoleId,
        Long newRoleId,
        Long actorId,
        LocalDateTime timestamp
) {}
