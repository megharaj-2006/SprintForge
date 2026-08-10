package org.SprintForge.modules.workflow.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeExecutionResult {
    private String status; // SUCCESS, FAILURE, PAUSED, SKIPPED
    @Builder.Default
    private Map<String, Object> outputs = new HashMap<>();
    private long executionTimeMillis;
    private String errorMessage;
    @Builder.Default
    private List<String> warnings = new ArrayList<>();
    @Builder.Default
    private List<String> logs = new ArrayList<>();
    private String nextBranchLabel; // SUCCESS, FAILURE, DEFAULT, TRUE, FALSE, CUSTOM

    public static NodeExecutionResult success(Map<String, Object> outputs) {
        return NodeExecutionResult.builder().status("SUCCESS").outputs(outputs).nextBranchLabel("SUCCESS").build();
    }

    public static NodeExecutionResult success(Map<String, Object> outputs, String branch) {
        return NodeExecutionResult.builder().status("SUCCESS").outputs(outputs).nextBranchLabel(branch).build();
    }

    public static NodeExecutionResult failure(String errorMessage) {
        return NodeExecutionResult.builder().status("FAILURE").errorMessage(errorMessage).nextBranchLabel("FAILURE").build();
    }

    public static NodeExecutionResult paused(String message) {
        return NodeExecutionResult.builder().status("PAUSED").nextBranchLabel("DEFAULT").build();
    }
}
