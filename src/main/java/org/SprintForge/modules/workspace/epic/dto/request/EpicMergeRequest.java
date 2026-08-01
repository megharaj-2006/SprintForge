package org.SprintForge.modules.workspace.epic.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EpicMergeRequest {

    @NotNull(message = "Target epic ID is required")
    private Long targetEpicId;
}
