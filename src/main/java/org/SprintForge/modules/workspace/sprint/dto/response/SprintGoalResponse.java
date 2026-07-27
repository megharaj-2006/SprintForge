package org.SprintForge.modules.workspace.sprint.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintGoalResponse {

    private Long id;
    private Long sprintId;
    private String goalText;
    private boolean achieved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
