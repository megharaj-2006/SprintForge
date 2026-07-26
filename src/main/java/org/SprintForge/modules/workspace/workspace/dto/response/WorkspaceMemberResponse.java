package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMemberResponse {

    private Long id;
    private Long workspaceId;
    private Long userId;
    private String userName;
    private String userEmail;
    private String avatarUrl;
    private Long roleId;
    private String roleName;
    private String jobTitle;
    private String department;
    private WorkspaceMemberStatus status;
    private Boolean joinedViaInvite;
    private LocalDateTime joinedAt;
    private LocalDateTime lastSeenAt;
    private Boolean isFavoriteWorkspace;
    private Boolean isStarred;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
