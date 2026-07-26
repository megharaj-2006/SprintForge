package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceRecoveryHistoryResponse {

    private Long id;
    private Long workspaceId;
    private String operationType;
    private String performedBy;
    private LocalDateTime timestamp;
    private String details;
}
