package org.SprintForge.modules.integration.sdk;

public interface ConnectorClient {
    String getProviderKey();
    ConnectorDescriptor getDescriptor();
    ConnectorResponse execute(ConnectorRequest request);
    boolean isHealthy();
}
