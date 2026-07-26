package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspacePreferenceResponse {

    private Long id;
    private Long workspaceId;
    private Long userId;
    private String theme;
    private boolean emailNotifications;
    private boolean pushNotifications;
    private boolean inAppNotifications;
    private boolean sidebarCollapsed;
}
