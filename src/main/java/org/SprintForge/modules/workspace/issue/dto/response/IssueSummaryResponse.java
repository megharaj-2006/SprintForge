package org.SprintForge.modules.workspace.issue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueSummaryResponse {

    private Long id;
    private String title;
    private String priority;
    private String status;
    private String assigneeName;
}
