package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceMemberStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMemberDetailResponse {

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
    private Long notificationPreferenceId;
    private List<String> permissions;
    private List<Long> assignedProjectIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
