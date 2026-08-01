package org.SprintForge.modules.workspace.epic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EpicProgressResponse {

    private Long epicId;
    private String epicName;
    private int totalTaskCount;
    private int completedTaskCount;
    private double completedPercentage;
    private int totalStoryPoints;
    private int completedStoryPoints;
    private double estimatedHours;
    private double remainingHours;
}
