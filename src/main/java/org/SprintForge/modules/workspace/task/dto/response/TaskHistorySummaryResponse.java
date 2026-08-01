package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.task.entity.enums.TaskHistoryActionType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskHistorySummaryResponse {
    private Long id;
    private TaskHistoryActionType actionType;
    private String description;
    private String performedByName;
    private LocalDateTime createdAt;
}
