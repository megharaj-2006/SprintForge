package org.SprintForge.modules.workspace.workspace.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceTransferOwnershipRequest {

    @NotNull(message = "New owner ID is required")
    private Long newOwnerId;

    private String confirmationToken;
    private String reason;
}
