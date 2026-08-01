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
public class MyTasksSummaryResponse {

    private Long userId;
    private int assignedCount;
    private int dueTodayCount;
    private int overdueCount;
    private int blockedCount;
    private List<TaskResponse> assignedTasks;
    private List<TaskResponse> dueTodayTasks;
    private List<TaskResponse> overdueTasks;
}
