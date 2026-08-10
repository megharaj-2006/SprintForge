package org.SprintForge.modules.integration.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectorRequest {
    private String providerKey;
    private String capabilityKey;
    private Long connectionId;
    private String httpMethod;
    private String endpointUrl;
    @Builder.Default
    private Map<String, String> headers = new HashMap<>();
    @Builder.Default
    private Map<String, Object> payload = new HashMap<>();
    @Builder.Default
    private Map<String, Object> workflowContext = new HashMap<>();
    @Builder.Default
    private Integer timeoutSeconds = 30;
}
