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
public class OperationContext {
    private String providerKey;
    private String operationKey;
    private Long connectionId;
    @Builder.Default
    private Map<String, Object> inputs = new HashMap<>();
    @Builder.Default
    private Map<String, Object> workflowContext = new HashMap<>();
}
