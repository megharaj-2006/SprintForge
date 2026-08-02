package org.SprintForge.modules.workspace.project.insights.reporting.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.project.insights.reporting.entity.enums.ReportFormat;
import org.SprintForge.modules.workspace.project.insights.reporting.entity.enums.ReportType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReportRequest {

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotBlank(message = "Report name is required")
    @Size(min = 2, max = 150, message = "Report name must be between 2 and 150 characters")
    private String name;

    private ReportType reportType;
    private ReportFormat format;
    private String parameters;
}
