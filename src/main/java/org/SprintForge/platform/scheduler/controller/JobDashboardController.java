package org.SprintForge.platform.scheduler.controller;

import org.SprintForge.platform.scheduler.dashboard.JobDashboardService;
import org.SprintForge.platform.scheduler.dto.JobStatisticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("platformJobDashboardController")
@RequestMapping("/platform/jobs")
@PreAuthorize("hasRole('ADMIN')")
public class JobDashboardController {

    private final JobDashboardService dashboardService;

    @Autowired
    public JobDashboardController(JobDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<JobStatisticsResponse> getDashboard() {
        return ResponseEntity.ok(dashboardService.getJobStatistics());
    }

    @GetMapping("/statistics")
    public ResponseEntity<JobStatisticsResponse> getStatistics() {
        return ResponseEntity.ok(dashboardService.getJobStatistics());
    }

    @GetMapping("/health")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok("HEALTHY");
    }
}
