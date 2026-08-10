package org.SprintForge.modules.integration.sdk;

import java.util.List;

public interface IntegrationConnectorPackage {
    String providerKey();
    List<ConnectorOperation> operations();
    List<ConnectorDescriptor> descriptors();
    ConnectorClient client();
}
