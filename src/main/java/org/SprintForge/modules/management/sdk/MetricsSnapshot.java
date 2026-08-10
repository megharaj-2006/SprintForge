package org.SprintForge.modules.management.sdk;

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
public class MetricsSnapshot {
    private String moduleId;
    @Builder.Default
    private Map<String, Object> metrics = new HashMap<>();
    private long uptimeSeconds;
}
