package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceDefaultView;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceVisibility;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceDetailResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String icon;
    private String coverImage;
    private WorkspaceVisibility visibility;
    private Long ownerId;
    private String ownerName;
    private String ownerEmail;
    private WorkspaceDefaultView defaultView;
    private String inviteCode;
    private Long storageUsed;
    private Long storageLimit;
    private Integer maxMembers;
    private Integer activeMemberCount;
    private Integer projectCount;
    private boolean isArchived;
    private WorkspaceSettingsResponse settings;
    private List<WorkspaceRoleResponse> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
