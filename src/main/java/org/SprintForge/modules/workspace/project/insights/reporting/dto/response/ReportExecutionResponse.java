package org.SprintForge.modules.workspace.project.insights.reporting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportExecutionResponse {

    private Long id;
    private Long reportId;
    private LocalDateTime executedAt;
    private String status;
    private String downloadUrl;
}
