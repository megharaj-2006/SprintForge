package org.SprintForge.modules.integration.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.integration.registry.ConnectorOperationRegistry;
import org.SprintForge.modules.integration.sdk.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/integrations")
@RequiredArgsConstructor
public class ConnectorPackageController {

    private final ConnectorOperationRegistry operationRegistry;

    @GetMapping("/packages")
    public ResponseEntity<List<String>> getPackages() {
        List<String> keys = operationRegistry.getAllPackages().stream()
                .map(IntegrationConnectorPackage::providerKey)
                .collect(Collectors.toList());
        return ResponseEntity.ok(keys);
    }

    @GetMapping("/packages/{packageKey}")
    public ResponseEntity<ConnectorDescriptor> getPackageDetails(@PathVariable String packageKey) {
        IntegrationConnectorPackage pkg = operationRegistry.getPackage(packageKey)
                .orElseThrow(() -> new ResourceNotFoundException("Connector package not found: " + packageKey));

        ConnectorDescriptor descriptor = pkg.descriptors() != null && !pkg.descriptors().isEmpty()
                ? pkg.descriptors().get(0)
                : ConnectorDescriptor.builder().connectorKey(packageKey).displayName(packageKey).build();

        return ResponseEntity.ok(descriptor);
    }

    @GetMapping("/packages/{packageKey}/operations")
    public ResponseEntity<List<OperationDescriptor>> getPackageOperations(@PathVariable String packageKey) {
        List<OperationDescriptor> ops = operationRegistry.getOperationsForPackage(packageKey).stream()
                .map(ConnectorOperation::getDescriptor)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ops);
    }

    @GetMapping("/packages/{packageKey}/health")
    public ResponseEntity<Map<String, Boolean>> getPackageHealth(@PathVariable String packageKey) {
        IntegrationConnectorPackage pkg = operationRegistry.getPackage(packageKey)
                .orElseThrow(() -> new ResourceNotFoundException("Connector package not found: " + packageKey));
        boolean healthy = pkg.client() != null && pkg.client().isHealthy();
        return ResponseEntity.ok(Map.of("healthy", healthy));
    }

    @Data
    public static class TestIntegrationRequest {
        private String providerKey;
        private String operationKey;
        private Map<String, Object> inputs;
    }

    @PostMapping("/test")
    public ResponseEntity<OperationResult> testIntegration(@RequestBody TestIntegrationRequest request) {
        OperationContext context = OperationContext.builder()
                .providerKey(request.getProviderKey())
                .operationKey(request.getOperationKey())
                .inputs(request.getInputs())
                .build();

        return ResponseEntity.ok(operationRegistry.executeOperation(context));
    }
}
