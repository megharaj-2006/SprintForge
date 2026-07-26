package org.SprintForge.modules.workspace.sprint.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintSummaryResponse {

    private Long id;
    private String name;
    private SprintStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer plannedStoryPoints;
    private Integer completedStoryPoints;
}
