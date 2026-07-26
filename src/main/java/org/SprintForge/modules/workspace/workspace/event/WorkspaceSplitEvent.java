package org.SprintForge.modules.workspace.workspace.event;

import java.time.LocalDateTime;
import java.util.List;

public record WorkspaceSplitEvent(
        Long sourceWorkspaceId,
        Long targetWorkspaceId,
        Long performerId,
        List<Long> projectIds,
        LocalDateTime timestamp
) {}
