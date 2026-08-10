package org.SprintForge.modules.workspace.workspace.branding.dto.request;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WorkspaceBrandingUpdateRequest {
    private String logo;
    private String banner;
    private String icon;
    private String theme;
    private String accentColor;
    private String primaryColor;
    private String fontFamily;
}
