package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskHealthResponse {

    private Long taskId;
    private int healthScore; // 0 to 100
    private String healthStatus; // HEALTHY, WARNING, CRITICAL
    private boolean isOverdue;
    private boolean isBlocked;
    private boolean isStale;
    private boolean missingAssignee;
    private boolean missingEstimate;
    private boolean missingDueDate;
    private List<String> healthWarnings;
}
