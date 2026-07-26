package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceDefaultView;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceVisibility;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String icon;
    private String coverImage;
    private WorkspaceVisibility visibility;
    private Long ownerId;
    private Long defaultRoleId;
    private Long defaultTaskStatusId;
    private Long defaultTaskPriorityId;
    private WorkspaceDefaultView defaultView;
    private String inviteCode;
    private Long storageUsed;
    private Long storageLimit;
    private Integer maxMembers;
    private boolean isArchived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
