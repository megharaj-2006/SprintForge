package org.SprintForge.modules.integration.packages;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.integration.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class GoogleConnectorPackage implements IntegrationConnectorPackage {

    @Override
    public String providerKey() {
        return "google";
    }

    @Override
    public List<ConnectorOperation> operations() {
        return List.of(
                new ConnectorOperation() {
                    @Override public String getOperationKey() { return "CREATE_CALENDAR_EVENT"; }
                    @Override
                    public OperationDescriptor getDescriptor() {
                        return OperationDescriptor.builder().operationKey("CREATE_CALENDAR_EVENT").displayName("Create Google Calendar Event").build();
                    }
                    @Override public boolean validate(OperationContext context) { return true; }
                    @Override
                    public OperationResult execute(OperationContext context) {
                        return OperationResult.success(Map.of("eventId", "gcal-777", "summary", context.getInputs().getOrDefault("summary", "Meeting")));
                    }
                }
        );
    }

    @Override
    public List<ConnectorDescriptor> descriptors() {
        return List.of(
                ConnectorDescriptor.builder()
                        .connectorKey("google")
                        .displayName("Google Workspace Connector Package")
                        .category("ENTERPRISE")
                        .version("1.0.0")
                        .supportedCapabilities(List.of("SEND_GMAIL", "CREATE_CALENDAR_EVENT", "UPLOAD_DRIVE_FILE", "CREATE_DOCUMENT", "APPEND_SHEET_ROW"))
                        .authenticationTypes(List.of("OAUTH2"))
                        .build()
        );
    }

    @Override
    public ConnectorClient client() {
        return new ConnectorClient() {
            @Override public String getProviderKey() { return "google"; }
            @Override public ConnectorDescriptor getDescriptor() { return descriptors().get(0); }
            @Override public ConnectorResponse execute(ConnectorRequest request) { return ConnectorResponse.success(Map.of("googleWorkspace", true)); }
            @Override public boolean isHealthy() { return true; }
        };
    }
}
