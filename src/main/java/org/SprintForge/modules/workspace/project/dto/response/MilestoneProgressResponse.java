package org.SprintForge.modules.workspace.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneProgressResponse {

    private Long milestoneId;
    private String milestoneName;
    private long totalTasks;
    private long completedTasks;
    private double progressPercentage;
}
