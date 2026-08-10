package org.SprintForge.modules.workspace.workspace.preference.dto.request;

import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WorkspaceGlobalPreferenceRequest {
    private String timezone;
    private String language;
    private String dateFormat;
    private String timeFormat;
    private String weekStartsOn;
    private List<String> workingDays;
    private String businessHoursStart;
    private String businessHoursEnd;
}
