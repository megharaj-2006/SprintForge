package org.SprintForge.modules.workspace.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatisticsResponse {
    private long totalTasks;
    private long todoTasks;
    private long inProgressTasks;
    private long inReviewTasks;
    private long doneTasks;
    private long cancelledTasks;
    private double totalEstimatedHours;
    private double totalActualHours;
    private int totalStoryPoints;
}
