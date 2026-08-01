package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRelationshipRequest {

    @NotNull(message = "Target task ID is required")
    private Long targetTaskId;

    @NotBlank(message = "Relationship type is required")
    private String relationshipType; // BLOCKS, BLOCKED_BY, DUPLICATE, RELATES_TO, PARENT, CHILD, SPLIT_FROM, MERGED_INTO, COPIED_FROM
}
