package org.SprintForge.platform.reporting.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.platform.reporting.domain.PlatformReport;
import org.SprintForge.platform.reporting.dto.GenerateReportRequest;
import org.SprintForge.platform.reporting.dto.ReportResponse;
import org.SprintForge.platform.reporting.repository.PlatformReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("platformReportPlatformService")
public class ReportPlatformService {

    private static final Logger log = LoggerFactory.getLogger(ReportPlatformService.class);
    private final PlatformReportRepository reportRepository;

    @Autowired
    public ReportPlatformService(PlatformReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    public ReportResponse generateReport(GenerateReportRequest request) {
        String downloadUrl = "/files/reports/" + UUID.randomUUID() + "." + request.getFormat().toLowerCase();
        PlatformReport report = PlatformReport.builder()
                .name(request.getName())
                .reportType(request.getReportType())
                .filters(request.getFilters())
                .generatedBy(request.getGeneratedBy())
                .format(request.getFormat().toUpperCase())
                .downloadUrl(downloadUrl)
                .status("COMPLETED")
                .build();

        PlatformReport saved = reportRepository.save(report);
        log.info("Generated platform report id={} name={} format={}", saved.getId(), saved.getName(), saved.getFormat());
        return toReportResponse(saved);
    }

    public ReportResponse getReport(Long id) {
        PlatformReport report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with ID: " + id));
        return toReportResponse(report);
    }

    public List<ReportResponse> getUserReports(String userId, int page, int size) {
        return reportRepository.findByGeneratedBy(userId, PageRequest.of(page, size)).stream()
                .map(this::toReportResponse)
                .collect(Collectors.toList());
    }

    public List<ReportResponse> getAllReports(int page, int size) {
        return reportRepository.findAll(PageRequest.of(page, size)).getContent().stream()
                .map(this::toReportResponse)
                .collect(Collectors.toList());
    }

    private ReportResponse toReportResponse(PlatformReport report) {
        return ReportResponse.builder()
                .id(report.getId())
                .name(report.getName())
                .reportType(report.getReportType())
                .filters(report.getFilters())
                .generatedBy(report.getGeneratedBy())
                .generatedAt(report.getGeneratedAt())
                .format(report.getFormat())
                .downloadUrl(report.getDownloadUrl())
                .status(report.getStatus())
                .build();
    }
}
