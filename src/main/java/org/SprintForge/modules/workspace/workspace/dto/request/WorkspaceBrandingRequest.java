package org.SprintForge.modules.workspace.workspace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceBrandingRequest {

    private String logo;
    private String icon;
    private String banner;
    private String primaryColor;
    private String secondaryColor;
    private String theme;
    private String customDomain;
    private String favicon;
}
