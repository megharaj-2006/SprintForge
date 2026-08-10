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
public class OperationResult {
    private String status; // SUCCESS, FAILURE
    private int statusCode;
    @Builder.Default
    private Map<String, Object> outputs = new HashMap<>();
    private String errorMessage;
    private long durationMs;

    public static OperationResult success(Map<String, Object> outputs) {
        return OperationResult.builder()
                .status("SUCCESS")
                .statusCode(200)
                .outputs(outputs != null ? outputs : Map.of())
                .build();
    }
}
