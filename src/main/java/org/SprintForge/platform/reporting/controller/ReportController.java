package org.SprintForge.platform.reporting.controller;

import org.SprintForge.platform.reporting.dto.GenerateReportRequest;
import org.SprintForge.platform.reporting.dto.ReportResponse;
import org.SprintForge.platform.reporting.service.ReportPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformReportController")
@RequestMapping("/platform/reports")
public class ReportController {

    private final ReportPlatformService reportService;

    @Autowired
    public ReportController(ReportPlatformService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<ReportResponse> generateReport(@RequestBody GenerateReportRequest request) {
        return ResponseEntity.ok(reportService.generateReport(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reportService.getReport(id));
    }

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAllReports(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(reportService.getAllReports(page, size));
    }
}
