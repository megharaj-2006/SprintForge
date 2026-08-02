package org.SprintForge.modules.workspace.project.event;

public record ProjectLeadChangedEvent(
        Long projectId,
        Long oldLeadId,
        Long newLeadId,
        Long actorId
) {}
