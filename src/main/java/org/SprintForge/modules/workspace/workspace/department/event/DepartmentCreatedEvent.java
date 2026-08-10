package org.SprintForge.modules.workspace.workspace.department.event;

import java.time.LocalDateTime;

public record DepartmentCreatedEvent(
    Long departmentId, 
    Long workspaceId, 
    Long createdById, 
    LocalDateTime timestamp
) {}
