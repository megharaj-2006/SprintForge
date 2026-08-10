package org.SprintForge.modules.ai.context;

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
public class AIContext {
    private String userId;
    private Long workspaceId;
    private Long projectId;
    private Long workflowId;
    private Long executionId;
    private String conversationId;
    @Builder.Default
    private Map<String, Object> variables = new HashMap<>();
    @Builder.Default
    private Map<String, Object> memoryState = new HashMap<>();
    @Builder.Default
    private Map<String, Object> configuration = new HashMap<>();
}
