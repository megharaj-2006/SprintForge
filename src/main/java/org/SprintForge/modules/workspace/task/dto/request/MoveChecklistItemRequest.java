package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveChecklistItemRequest {

    @NotNull(message = "Item ID is required")
    private Long itemId;

    @NotNull(message = "Position is required")
    private Integer position;
}
