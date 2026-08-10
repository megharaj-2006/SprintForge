package org.SprintForge.modules.integration.packages;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.integration.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MicrosoftConnectorPackage implements IntegrationConnectorPackage {

    @Override
    public String providerKey() {
        return "microsoft";
    }

    @Override
    public List<ConnectorOperation> operations() {
        return List.of(
                new ConnectorOperation() {
                    @Override public String getOperationKey() { return "SEND_TEAMS_MESSAGE"; }
                    @Override
                    public OperationDescriptor getDescriptor() {
                        return OperationDescriptor.builder().operationKey("SEND_TEAMS_MESSAGE").displayName("Send Teams Message").build();
                    }
                    @Override public boolean validate(OperationContext context) { return true; }
                    @Override
                    public OperationResult execute(OperationContext context) {
                        return OperationResult.success(Map.of("messageId", "ms-teams-99", "sent", true));
                    }
                },
                new ConnectorOperation() {
                    @Override public String getOperationKey() { return "SEND_OUTLOOK_EMAIL"; }
                    @Override
                    public OperationDescriptor getDescriptor() {
                        return OperationDescriptor.builder().operationKey("SEND_OUTLOOK_EMAIL").displayName("Send Outlook Email").build();
                    }
                    @Override public boolean validate(OperationContext context) { return true; }
                    @Override
                    public OperationResult execute(OperationContext context) {
                        return OperationResult.success(Map.of("emailId", "outlook-101", "sent", true));
                    }
                }
        );
    }

    @Override
    public List<ConnectorDescriptor> descriptors() {
        return List.of(
                ConnectorDescriptor.builder()
                        .connectorKey("microsoft")
                        .displayName("Microsoft Graph Connector Package")
                        .category("ENTERPRISE")
                        .version("1.0.0")
                        .supportedCapabilities(List.of("SEND_TEAMS_MESSAGE", "SEND_OUTLOOK_EMAIL", "CREATE_CALENDAR_EVENT", "UPLOAD_ONEDRIVE_FILE"))
                        .authenticationTypes(List.of("OAUTH2"))
                        .build()
        );
    }

    @Override
    public ConnectorClient client() {
        return new ConnectorClient() {
            @Override public String getProviderKey() { return "microsoft"; }
            @Override public ConnectorDescriptor getDescriptor() { return descriptors().get(0); }
            @Override public ConnectorResponse execute(ConnectorRequest request) { return ConnectorResponse.success(Map.of("microsoftGraph", true)); }
            @Override public boolean isHealthy() { return true; }
        };
    }
}
