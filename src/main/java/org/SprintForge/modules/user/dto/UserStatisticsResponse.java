package org.SprintForge.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticsResponse {
    private Long userId;
    private long totalWorkspaces;
    private long totalProjects;
    private long assignedTasksCount;
    private long completedTasksCount;
    private long totalCommentsCount;
}
