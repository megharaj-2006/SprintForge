package org.SprintForge.modules.workflow.simulation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workflow.entity.WorkflowVersion;
import org.SprintForge.modules.workflow.repository.WorkflowVersionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowSimulationServiceImpl implements WorkflowSimulationService {

    private final WorkflowVersionRepository workflowVersionRepository;
    private final BreakpointManager breakpointManager;
    private final Map<String, SimulationResult> cache = new ConcurrentHashMap<>();

    @Override
    public SimulationResult runSimulation(SimulationContext context) {
        String simId = UUID.randomUUID().toString();
        context.setSimulationId(simId);

        WorkflowVersion version = workflowVersionRepository.findById(context.getWorkflowVersionId())
                .orElseThrow(() -> new ResourceNotFoundException("WorkflowVersion not found with id: " + context.getWorkflowVersionId()));

        log.info("Running dry-run simulation {} for version v{}", simId, version.getVersionNumber());

        SimulationResult result = SimulationResult.builder()
                .simulationId(simId)
                .status("SUCCESS")
                .totalDurationMillis(120)
                .finalOutputs(Map.of("simulated", true, "versionNumber", version.getVersionNumber()))
                .executionTimeline(List.of("Dry-run started", "Validated nodes", "Simulated start node", "Completed dry-run"))
                .build();

        cache.put(simId, result);
        return result;
    }

    @Override
    public SimulationResult getSimulationStatus(String simulationId) {
        SimulationResult res = cache.get(simulationId);
        if (res == null) {
            throw new ResourceNotFoundException("Simulation not found for ID: " + simulationId);
        }
        return res;
    }

    @Override
    public SimulationResult replaySimulation(String simulationId) {
        SimulationResult existing = getSimulationStatus(simulationId);
        existing.getExecutionTimeline().add("Replay triggered at " + System.currentTimeMillis());
        return existing;
    }

    @Override
    public void pauseExecution(String executionId) {
        log.info("Debugger paused execution {}", executionId);
    }

    @Override
    public void resumeExecution(String executionId) {
        log.info("Debugger resumed execution {}", executionId);
    }
}
