package org.SprintForge.modules.workspace.kanban.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateRequest {

    @Size(min = 2, max = 100, message = "Board name must be between 2 and 100 characters")
    private String name;

    private String description;
    private String type;
}
