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
public class ConnectorResponse {
    private int statusCode;
    private String status; // SUCCESS, FAILURE
    @Builder.Default
    private Map<String, Object> payload = new HashMap<>();
    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();
    private long latencyMs;
    private String errorMessage;

    public static ConnectorResponse success(Map<String, Object> payload) {
        return ConnectorResponse.builder()
                .statusCode(200)
                .status("SUCCESS")
                .payload(payload != null ? payload : Map.of())
                .build();
    }
}
