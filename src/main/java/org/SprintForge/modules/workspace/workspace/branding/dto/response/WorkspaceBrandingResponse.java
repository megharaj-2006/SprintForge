package org.SprintForge.modules.workspace.workspace.branding.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WorkspaceBrandingResponse {
    private Long id;
    private Long workspaceId;
    private String logo;
    private String banner;
    private String icon;
    private String theme;
    private String accentColor;
    private String primaryColor;
    private String fontFamily;
    private LocalDateTime updatedAt;
}
