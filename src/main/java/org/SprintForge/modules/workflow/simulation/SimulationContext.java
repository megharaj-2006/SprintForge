package org.SprintForge.modules.workflow.simulation;

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
public class SimulationContext {
    private String simulationId;
    private Long workflowVersionId;
    private boolean dryRun;
    private boolean stepByStep;
    @Builder.Default
    private Map<String, Object> mockVariables = new HashMap<>();
    @Builder.Default
    private Map<String, Object> mockedNodeOutputs = new HashMap<>();
}
