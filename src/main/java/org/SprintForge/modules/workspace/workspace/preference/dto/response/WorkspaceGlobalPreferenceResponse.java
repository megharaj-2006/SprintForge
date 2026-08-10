package org.SprintForge.modules.workspace.workspace.preference.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WorkspaceGlobalPreferenceResponse {
    private Long workspaceId;
    private String timezone;
    private String language;
    private String dateFormat;
    private String timeFormat;
    private String weekStartsOn;
    private List<String> workingDays;
    private String businessHoursStart;
    private String businessHoursEnd;
    private LocalDateTime updatedAt;
}
