package org.SprintForge.modules.workspace.kanban.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardSummaryResponse {

    private Long id;
    private String name;
    private String type;
    private Integer columnCount;
}
