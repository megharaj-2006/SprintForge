package org.SprintForge.modules.workspace.sprint.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintCompleteRequest {

    private Long moveRemainingTasksToSprintId; // null means move to backlog
}
