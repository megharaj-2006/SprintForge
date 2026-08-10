package org.SprintForge.modules.integration.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.integration.dto.ConnectionResponse;
import org.SprintForge.modules.integration.dto.CreateConnectionRequest;
import org.SprintForge.modules.integration.entity.IntegrationConnection;
import org.SprintForge.modules.integration.entity.IntegrationProvider;
import org.SprintForge.modules.integration.registry.ConnectorRegistry;
import org.SprintForge.modules.integration.repository.IntegrationConnectionRepository;
import org.SprintForge.modules.integration.repository.IntegrationProviderRepository;
import org.SprintForge.modules.integration.sdk.ConnectorClient;
import org.SprintForge.modules.integration.sdk.ConnectorDescriptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IntegrationPlatformServiceImpl implements IntegrationPlatformService {

    private final IntegrationProviderRepository providerRepository;
    private final IntegrationConnectionRepository connectionRepository;
    private final ConnectorRegistry connectorRegistry;

    @Override
    @Transactional(readOnly = true)
    public List<IntegrationProvider> getProviders() {
        return providerRepository.findAll();
    }

    @Override
    public List<ConnectorDescriptor> getConnectors() {
        return connectorRegistry.getAllClients().stream()
                .map(ConnectorClient::getDescriptor)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ConnectionResponse createConnection(CreateConnectionRequest request) {
        IntegrationProvider provider = providerRepository.findByProviderKey(request.getProviderKey())
                .orElseThrow(() -> new ResourceNotFoundException("IntegrationProvider not found for key: " + request.getProviderKey()));

        IntegrationConnection conn = new IntegrationConnection();
        conn.setWorkspaceId(request.getWorkspaceId());
        conn.setProvider(provider);
        conn.setConnectionName(request.getConnectionName());
        conn.setAuthenticationType(request.getAuthenticationType() != null ? request.getAuthenticationType() : "API_KEY");
        conn.setBaseUrl(request.getBaseUrl());
        conn.setHealthStatus("HEALTHY");
        conn.setLastConnected(LocalDateTime.now());

        IntegrationConnection saved = connectionRepository.save(conn);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public ConnectionResponse updateConnection(Long id, CreateConnectionRequest request) {
        IntegrationConnection conn = connectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IntegrationConnection not found with id: " + id));

        if (request.getConnectionName() != null) conn.setConnectionName(request.getConnectionName());
        if (request.getBaseUrl() != null) conn.setBaseUrl(request.getBaseUrl());

        IntegrationConnection saved = connectionRepository.save(conn);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateConnection(Long id) {
        IntegrationConnection conn = connectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IntegrationConnection not found with id: " + id));
        ConnectorClient client = connectorRegistry.getClient(conn.getProvider().getProviderKey());
        return client.isHealthy();
    }

    private ConnectionResponse mapToResponse(IntegrationConnection conn) {
        return ConnectionResponse.builder()
                .id(conn.getId())
                .connectionName(conn.getConnectionName())
                .providerKey(conn.getProvider().getProviderKey())
                .status(conn.getStatus().name())
                .healthStatus(conn.getHealthStatus())
                .baseUrl(conn.getBaseUrl())
                .build();
    }
}
