package org.SprintForge.modules.workflow.simulation;

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
public class SimulationResult {
    private String simulationId;
    private String status; // SUCCESS, PAUSED, FAILED
    private long totalDurationMillis;
    @Builder.Default
    private Map<String, Object> finalOutputs = new HashMap<>();
    @Builder.Default
    private List<String> executionTimeline = new ArrayList<>();
    @Builder.Default
    private List<String> warnings = new ArrayList<>();
}
