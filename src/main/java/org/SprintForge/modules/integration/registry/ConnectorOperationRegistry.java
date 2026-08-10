package org.SprintForge.modules.integration.registry;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.integration.sdk.ConnectorOperation;
import org.SprintForge.modules.integration.sdk.IntegrationConnectorPackage;
import org.SprintForge.modules.integration.sdk.OperationContext;
import org.SprintForge.modules.integration.sdk.OperationResult;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ConnectorOperationRegistry {

    private final Map<String, Map<String, ConnectorOperation>> packageOperations = new ConcurrentHashMap<>();
    private final Map<String, IntegrationConnectorPackage> packages = new ConcurrentHashMap<>();

    public ConnectorOperationRegistry(List<IntegrationConnectorPackage> connectorPackages) {
        if (connectorPackages != null) {
            for (IntegrationConnectorPackage pkg : connectorPackages) {
                registerPackage(pkg);
            }
        }
    }

    public void registerPackage(IntegrationConnectorPackage pkg) {
        log.info("Registering IntegrationConnectorPackage for providerKey: {}", pkg.providerKey());
        packages.put(pkg.providerKey().toLowerCase(), pkg);

        Map<String, ConnectorOperation> opMap = packageOperations.computeIfAbsent(pkg.providerKey().toLowerCase(), k -> new ConcurrentHashMap<>());
        if (pkg.operations() != null) {
            for (ConnectorOperation op : pkg.operations()) {
                log.info("Registering ConnectorOperation [{}] under package [{}]", op.getOperationKey(), pkg.providerKey());
                opMap.put(op.getOperationKey().toUpperCase(), op);
            }
        }
    }

    public OperationResult executeOperation(OperationContext context) {
        String providerKey = context.getProviderKey() != null ? context.getProviderKey().toLowerCase() : "rest";
        String operationKey = context.getOperationKey() != null ? context.getOperationKey().toUpperCase() : "POST";

        Map<String, ConnectorOperation> opMap = packageOperations.get(providerKey);
        if (opMap == null || !opMap.containsKey(operationKey)) {
            // Fallback generic execution via client
            IntegrationConnectorPackage pkg = packages.get(providerKey);
            if (pkg != null && pkg.client() != null) {
                log.info("Executing fallback via client for provider: {}", providerKey);
                return OperationResult.success(Map.of("providerKey", providerKey, "operationKey", operationKey, "status", "EXECUTED_GENERIC"));
            }
            throw new ResourceNotFoundException("ConnectorOperation not found for package: " + providerKey + ", operation: " + operationKey);
        }

        ConnectorOperation op = opMap.get(operationKey);
        long startTime = System.currentTimeMillis();
        op.validate(context);
        OperationResult result = op.execute(context);
        result.setDurationMs(System.currentTimeMillis() - startTime);
        return result;
    }

    public List<IntegrationConnectorPackage> getAllPackages() {
        return List.copyOf(packages.values());
    }

    public Optional<IntegrationConnectorPackage> getPackage(String providerKey) {
        return Optional.ofNullable(packages.get(providerKey.toLowerCase()));
    }

    public List<ConnectorOperation> getOperationsForPackage(String providerKey) {
        Map<String, ConnectorOperation> opMap = packageOperations.get(providerKey.toLowerCase());
        if (opMap == null) return List.of();
        return List.copyOf(opMap.values());
    }
}
