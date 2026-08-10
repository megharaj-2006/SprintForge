package org.SprintForge.modules.management.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthReport {
    private String moduleId;
    private HealthStatus status;
    private String message;
    @Builder.Default
    private Map<String, Object> details = new HashMap<>();
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static HealthReport up(String moduleId, String message) {
        return HealthReport.builder()
                .moduleId(moduleId)
                .status(HealthStatus.UP)
                .message(message)
                .build();
    }
}
