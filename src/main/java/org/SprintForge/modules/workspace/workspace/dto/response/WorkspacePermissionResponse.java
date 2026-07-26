package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspacePermissionResponse {

    private Long workspaceId;
    private Long userId;
    private String roleName;
    private Boolean isOwner;
    private Set<String> grantedPermissions;
}
