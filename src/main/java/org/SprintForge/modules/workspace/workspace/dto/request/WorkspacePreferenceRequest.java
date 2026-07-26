package org.SprintForge.modules.workspace.workspace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspacePreferenceRequest {

    private String theme;
    private Boolean emailNotifications;
    private Boolean pushNotifications;
    private Boolean inAppNotifications;
    private Boolean sidebarCollapsed;
}
