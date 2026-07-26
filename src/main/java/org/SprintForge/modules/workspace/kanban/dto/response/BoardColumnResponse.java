package org.SprintForge.modules.workspace.kanban.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardColumnResponse {

    private Long id;
    private Long boardId;
    private String name;
    private Integer position;
    private Integer limit;
}
