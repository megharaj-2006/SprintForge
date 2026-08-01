package org.SprintForge.modules.workspace.timelog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartTimerRequest {
    private String description;
    private Boolean billable;
}
