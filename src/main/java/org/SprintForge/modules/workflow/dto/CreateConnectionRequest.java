package org.SprintForge.modules.workflow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.SprintForge.modules.workflow.entity.enums.BranchLabel;

@Data
public class CreateConnectionRequest {
    @NotNull(message = "Source node ID is required")
    private Long sourceNodeId;

    @NotNull(message = "Target node ID is required")
    private Long targetNodeId;

    private BranchLabel branchLabel = BranchLabel.DEFAULT;
    private Integer priority = 0;
    private String conditionExpression;
}
