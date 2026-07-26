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
public class WorkspaceAuditResponse {

    private Long id;
    private Long workspaceId;
    private Long userId;
    private String userName;
    private String action;
    private String category;
    private String details;
    private String ipAddress;
    private LocalDateTime timestamp;
}
