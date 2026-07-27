package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceInvitationStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationSummaryResponse {

    private Long id;
    private Long workspaceId;
    private String workspaceName;
    private String email;
    private Long roleId;
    private String roleName;
    private Long invitedBy;
    private String inviterName;
    private WorkspaceInvitationStatus status;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}
