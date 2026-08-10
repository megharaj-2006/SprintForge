package org.SprintForge.modules.integration.packages;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.integration.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class GithubConnectorPackage implements IntegrationConnectorPackage {

    @Override
    public String providerKey() {
        return "github";
    }

    @Override
    public List<ConnectorOperation> operations() {
        return List.of(
                new ConnectorOperation() {
                    @Override
                    public String getOperationKey() { return "CREATE_ISSUE"; }
                    @Override
                    public OperationDescriptor getDescriptor() {
                        return OperationDescriptor.builder().operationKey("CREATE_ISSUE").displayName("Create GitHub Issue").build();
                    }
                    @Override
                    public boolean validate(OperationContext context) { return true; }
                    @Override
                    public OperationResult execute(OperationContext context) {
                        return OperationResult.success(Map.of("issueNumber", 101, "repository", context.getInputs().getOrDefault("repository", "org/repo"), "status", "OPEN"));
                    }
                },
                new ConnectorOperation() {
                    @Override
                    public String getOperationKey() { return "CREATE_RELEASE"; }
                    @Override
                    public OperationDescriptor getDescriptor() {
                        return OperationDescriptor.builder().operationKey("CREATE_RELEASE").displayName("Create GitHub Release").build();
                    }
                    @Override
                    public boolean validate(OperationContext context) { return true; }
                    @Override
                    public OperationResult execute(OperationContext context) {
                        return OperationResult.success(Map.of("releaseId", 501, "tagName", context.getInputs().getOrDefault("tagName", "v1.0.0")));
                    }
                }
        );
    }

    @Override
    public List<ConnectorDescriptor> descriptors() {
        return List.of(
                ConnectorDescriptor.builder()
                        .connectorKey("github")
                        .displayName("GitHub Connector Package")
                        .category("DEVELOPMENT")
                        .version("1.0.0")
                        .supportedCapabilities(List.of("CREATE_ISSUE", "UPDATE_ISSUE", "CLOSE_ISSUE", "CREATE_RELEASE", "CREATE_BRANCH"))
                        .authenticationTypes(List.of("PAT", "OAUTH2"))
                        .build()
        );
    }

    @Override
    public ConnectorClient client() {
        return new ConnectorClient() {
            @Override public String getProviderKey() { return "github"; }
            @Override public ConnectorDescriptor getDescriptor() { return descriptors().get(0); }
            @Override
            public ConnectorResponse execute(ConnectorRequest request) {
                return ConnectorResponse.success(Map.of("github", true, "capability", request.getCapabilityKey()));
            }
            @Override public boolean isHealthy() { return true; }
        };
    }
}
