package org.SprintForge.modules.workspace.project.insights.reporting.service;

import org.SprintForge.modules.workspace.project.insights.reporting.dto.request.CreateReportRequest;
import org.SprintForge.modules.workspace.project.insights.reporting.dto.response.ReportExecutionResponse;
import org.SprintForge.modules.workspace.project.insights.reporting.dto.response.SavedReportResponse;

import java.util.List;

public interface ProjectReportService {
    SavedReportResponse createReport(CreateReportRequest request, Long actorId);
    List<SavedReportResponse> getProjectReports(Long projectId);
    ReportExecutionResponse generateReport(Long reportId, Long actorId);
}
