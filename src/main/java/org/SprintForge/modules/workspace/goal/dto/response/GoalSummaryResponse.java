package org.SprintForge.modules.workspace.goal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalSummaryResponse {

    private Long id;
    private String title;
    private String status;
    private String priority;
    private Double progressPercentage;
    private LocalDate targetDate;
}
