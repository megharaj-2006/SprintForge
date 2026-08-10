package org.SprintForge.modules.integration.controller;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.integration.dto.ConnectionResponse;
import org.SprintForge.modules.integration.dto.CreateConnectionRequest;
import org.SprintForge.modules.integration.entity.IntegrationProvider;
import org.SprintForge.modules.integration.monitoring.IntegrationMonitoringService;
import org.SprintForge.modules.integration.sdk.ConnectorDescriptor;
import org.SprintForge.modules.integration.service.IntegrationPlatformService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/integrations")
@RequiredArgsConstructor
public class IntegrationController {

    private final IntegrationPlatformService platformService;
    private final IntegrationMonitoringService monitoringService;

    @GetMapping("/providers")
    public ResponseEntity<List<IntegrationProvider>> getProviders() {
        return ResponseEntity.ok(platformService.getProviders());
    }

    @GetMapping("/connectors")
    public ResponseEntity<List<ConnectorDescriptor>> getConnectors() {
        return ResponseEntity.ok(platformService.getConnectors());
    }

    @PostMapping("/connections")
    public ResponseEntity<ConnectionResponse> createConnection(@RequestBody CreateConnectionRequest request) {
        return ResponseEntity.ok(platformService.createConnection(request));
    }

    @PatchMapping("/connections/{id}")
    public ResponseEntity<ConnectionResponse> updateConnection(@PathVariable Long id, @RequestBody CreateConnectionRequest request) {
        return ResponseEntity.ok(platformService.updateConnection(id, request));
    }

    @PostMapping("/connections/{id}/validate")
    public ResponseEntity<Map<String, Boolean>> validateConnection(@PathVariable Long id) {
        boolean valid = platformService.validateConnection(id);
        return ResponseEntity.ok(Map.of("valid", valid));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getHealth() {
        return ResponseEntity.ok(monitoringService.getHealthStatus());
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(monitoringService.getStatistics());
    }
}
