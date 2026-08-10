package org.SprintForge.modules.workflow.executor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeExecutorResult {
    private String status; // SUCCESS, FAILURE, PAUSED, SKIPPED
    @Builder.Default
    private Map<String, Object> outputData = new HashMap<>();
    private String errorMessage;

    public static NodeExecutorResult success() {
        return NodeExecutorResult.builder().status("SUCCESS").build();
    }

    public static NodeExecutorResult success(Map<String, Object> output) {
        return NodeExecutorResult.builder().status("SUCCESS").outputData(output).build();
    }

    public static NodeExecutorResult failure(String errorMessage) {
        return NodeExecutorResult.builder().status("FAILURE").errorMessage(errorMessage).build();
    }
}
