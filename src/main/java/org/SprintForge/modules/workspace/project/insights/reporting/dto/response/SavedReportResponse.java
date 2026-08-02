package org.SprintForge.modules.workspace.project.insights.reporting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.insights.reporting.entity.enums.ReportFormat;
import org.SprintForge.modules.workspace.project.insights.reporting.entity.enums.ReportType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedReportResponse {

    private Long id;
    private Long projectId;
    private String name;
    private ReportType reportType;
    private ReportFormat format;
    private String parameters;
    private Long createdBy;
    private LocalDateTime createdAt;
}
