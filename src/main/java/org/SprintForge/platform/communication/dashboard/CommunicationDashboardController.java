package org.SprintForge.platform.communication.dashboard;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.dto.ApiResponse;
import org.SprintForge.platform.communication.dto.CommunicationDashboardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("platformCommunicationDashboardController")
@RequestMapping("/platform/communication")
@RequiredArgsConstructor
public class CommunicationDashboardController {

    private final CommunicationDashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<CommunicationDashboardResponse>> getDashboard() {
        return ResponseEntity.ok(ApiResponse.success(dashboardService.getDashboardMetrics()));
    }

    @GetMapping("/metrics")
    public ResponseEntity<ApiResponse<CommunicationDashboardResponse>> getMetrics() {
        return ResponseEntity.ok(ApiResponse.success(dashboardService.getDashboardMetrics()));
    }
}
