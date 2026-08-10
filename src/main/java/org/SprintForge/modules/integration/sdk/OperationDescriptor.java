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
public class OperationDescriptor {
    private String operationKey;
    private String displayName;
    private String description;
    @Builder.Default
    private Map<String, Object> inputSchema = new HashMap<>();
    @Builder.Default
    private Map<String, Object> outputSchema = new HashMap<>();
}
