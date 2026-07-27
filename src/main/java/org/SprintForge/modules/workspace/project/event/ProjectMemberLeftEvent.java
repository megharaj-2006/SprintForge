package org.SprintForge.modules.workspace.project.event;

import java.time.LocalDateTime;

public record ProjectMemberLeftEvent(
        Long projectId,
        Long workspaceMemberId,
        LocalDateTime timestamp
) {}
