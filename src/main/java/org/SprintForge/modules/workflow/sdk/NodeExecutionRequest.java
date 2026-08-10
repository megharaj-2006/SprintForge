package org.SprintForge.modules.workflow.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeExecutionRequest {
    private NodeContext context;
    private NodeConfiguration configuration;
    private Map<String, Object> inputData;
}
