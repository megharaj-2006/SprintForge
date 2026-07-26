package org.SprintForge.modules.workspace.milestone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneSummaryResponse {

    private Long id;
    private String name;
    private String status;
    private LocalDate dueDate;
    private Double progressPercentage;
}
