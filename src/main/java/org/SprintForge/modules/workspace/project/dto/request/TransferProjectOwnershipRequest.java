package org.SprintForge.modules.workspace.project.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferProjectOwnershipRequest {

    @NotNull(message = "New project owner user ID is required")
    private Long newOwnerUserId;
}
