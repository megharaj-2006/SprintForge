package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkLabelRequest {

    @NotEmpty(message = "Task IDs are required")
    private List<Long> taskIds;

    private List<Long> labelIdsToAdd;

    private List<Long> labelIdsToRemove;
}
