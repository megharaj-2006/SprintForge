package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskOperationalReportResponse {

    private Long projectId;
    private int totalTasks;
    private int completedTasks;
    private int overdueTasks;
    private double averageTaskAgingDays;
    private Map<String, Long> tasksByStatus;
    private Map<String, Long> tasksByPriority;
}
