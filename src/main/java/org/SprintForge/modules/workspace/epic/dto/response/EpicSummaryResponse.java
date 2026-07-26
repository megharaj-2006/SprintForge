package org.SprintForge.modules.workspace.epic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EpicSummaryResponse {

    private Long id;
    private String name;
    private String color;
    private String status;
    private Double progressPercentage;
}
