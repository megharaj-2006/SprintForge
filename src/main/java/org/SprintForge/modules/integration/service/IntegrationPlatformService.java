package org.SprintForge.modules.integration.service;

import org.SprintForge.modules.integration.dto.ConnectionResponse;
import org.SprintForge.modules.integration.dto.CreateConnectionRequest;
import org.SprintForge.modules.integration.entity.IntegrationConnection;
import org.SprintForge.modules.integration.entity.IntegrationProvider;
import org.SprintForge.modules.integration.sdk.ConnectorDescriptor;

import java.util.List;

public interface IntegrationPlatformService {
    List<IntegrationProvider> getProviders();
    List<ConnectorDescriptor> getConnectors();
    ConnectionResponse createConnection(CreateConnectionRequest request);
    ConnectionResponse updateConnection(Long id, CreateConnectionRequest request);
    boolean validateConnection(Long id);
}
