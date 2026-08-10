package org.SprintForge.modules.integration.packages;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.integration.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SlackConnectorPackage implements IntegrationConnectorPackage {

    @Override
    public String providerKey() {
        return "slack";
    }

    @Override
    public List<ConnectorOperation> operations() {
        return List.of(
                new ConnectorOperation() {
                    @Override public String getOperationKey() { return "SEND_MESSAGE"; }
                    @Override
                    public OperationDescriptor getDescriptor() {
                        return OperationDescriptor.builder().operationKey("SEND_MESSAGE").displayName("Send Slack Message").build();
                    }
                    @Override public boolean validate(OperationContext context) { return true; }
                    @Override
                    public OperationResult execute(OperationContext context) {
                        return OperationResult.success(Map.of("ts", "1620000000.000100", "channel", context.getInputs().getOrDefault("channel", "#general"), "ok", true));
                    }
                }
        );
    }

    @Override
    public List<ConnectorDescriptor> descriptors() {
        return List.of(
                ConnectorDescriptor.builder()
                        .connectorKey("slack")
                        .displayName("Slack Connector Package")
                        .category("COMMUNICATION")
                        .version("1.0.0")
                        .supportedCapabilities(List.of("SEND_MESSAGE", "SEND_RICH_MESSAGE", "UPLOAD_FILE", "CREATE_CHANNEL"))
                        .authenticationTypes(List.of("BOT_TOKEN", "OAUTH2"))
                        .build()
        );
    }

    @Override
    public ConnectorClient client() {
        return new ConnectorClient() {
            @Override public String getProviderKey() { return "slack"; }
            @Override public ConnectorDescriptor getDescriptor() { return descriptors().get(0); }
            @Override
            public ConnectorResponse execute(ConnectorRequest request) {
                return ConnectorResponse.success(Map.of("slack", true, "messageSent", true));
            }
            @Override public boolean isHealthy() { return true; }
        };
    }
}
