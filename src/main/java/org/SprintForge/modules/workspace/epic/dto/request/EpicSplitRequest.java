package org.SprintForge.modules.workspace.epic.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class EpicSplitRequest {

    @NotBlank(message = "New epic name is required")
    private String newEpicName;

    @NotEmpty(message = "Task IDs to move are required")
    private List<Long> taskIdsToMove;
}
