package org.SprintForge.modules.workspace.project.insights.reporting.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.insights.reporting.dto.request.CreateReportRequest;
import org.SprintForge.modules.workspace.project.insights.reporting.dto.response.ReportExecutionResponse;
import org.SprintForge.modules.workspace.project.insights.reporting.dto.response.SavedReportResponse;
import org.SprintForge.modules.workspace.project.insights.reporting.entity.ReportExecution;
import org.SprintForge.modules.workspace.project.insights.reporting.entity.SavedReport;
import org.SprintForge.modules.workspace.project.insights.reporting.entity.enums.ReportFormat;
import org.SprintForge.modules.workspace.project.insights.reporting.entity.enums.ReportType;
import org.SprintForge.modules.workspace.project.insights.reporting.repository.ReportExecutionRepository;
import org.SprintForge.modules.workspace.project.insights.reporting.repository.SavedReportRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectReportServiceImpl implements ProjectReportService {

    private final SavedReportRepository savedReportRepository;
    private final ReportExecutionRepository reportExecutionRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public SavedReportResponse createReport(CreateReportRequest request, Long actorId) {
        Project project = projectRepository.findById(request.getProjectId())
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + request.getProjectId()));

        SavedReport report = new SavedReport();
        report.setProjectId(request.getProjectId());
        report.setName(request.getName());
        report.setReportType(request.getReportType() != null ? request.getReportType() : ReportType.EXECUTIVE_SUMMARY);
        report.setFormat(request.getFormat() != null ? request.getFormat() : ReportFormat.PDF);
        report.setParameters(request.getParameters());

        SavedReport saved = savedReportRepository.save(report);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SavedReportResponse> getProjectReports(Long projectId) {
        return savedReportRepository.findByProjectIdAndIsDeletedFalse(projectId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReportExecutionResponse generateReport(Long reportId, Long actorId) {
        SavedReport report = savedReportRepository.findById(reportId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Report template not found with ID: " + reportId));

        ReportExecution execution = new ReportExecution();
        execution.setReportId(report.getId());
        execution.setExecutedAt(LocalDateTime.now());
        execution.setStatus("COMPLETED");
        execution.setDownloadUrl("/api/v1/reports/download/" + report.getId() + "." + report.getFormat().name().toLowerCase());

        ReportExecution saved = reportExecutionRepository.save(execution);

        return ReportExecutionResponse.builder()
                .id(saved.getId())
                .reportId(saved.getReportId())
                .executedAt(saved.getExecutedAt())
                .status(saved.getStatus())
                .downloadUrl(saved.getDownloadUrl())
                .build();
    }

    private SavedReportResponse toResponse(SavedReport report) {
        Long creatorId = null;
        if (report.getCreatedBy() != null) {
            try {
                creatorId = Long.parseLong(report.getCreatedBy());
            } catch (NumberFormatException ignored) {}
        }

        return SavedReportResponse.builder()
                .id(report.getId())
                .projectId(report.getProjectId())
                .name(report.getName())
                .reportType(report.getReportType())
                .format(report.getFormat())
                .parameters(report.getParameters())
                .createdBy(creatorId)
                .createdAt(report.getCreatedAt())
                .build();
    }
}
