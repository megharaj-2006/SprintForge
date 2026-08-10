package org.SprintForge.modules.workflow.simulation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow")
@RequiredArgsConstructor
public class SimulationController {

    private final WorkflowSimulationService simulationService;

    @PostMapping("/simulations")
    public ResponseEntity<SimulationResult> runSimulation(@RequestBody SimulationContext context) {
        return ResponseEntity.ok(simulationService.runSimulation(context));
    }

    @GetMapping("/simulations/{id}")
    public ResponseEntity<SimulationResult> getSimulationStatus(@PathVariable String id) {
        return ResponseEntity.ok(simulationService.getSimulationStatus(id));
    }

    @PostMapping("/simulations/{id}/replay")
    public ResponseEntity<SimulationResult> replaySimulation(@PathVariable String id) {
        return ResponseEntity.ok(simulationService.replaySimulation(id));
    }

    @PostMapping("/debug/{executionId}/pause")
    public ResponseEntity<Void> pauseExecution(@PathVariable String executionId) {
        simulationService.pauseExecution(executionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/debug/{executionId}/resume")
    public ResponseEntity<Void> resumeExecution(@PathVariable String executionId) {
        simulationService.resumeExecution(executionId);
        return ResponseEntity.ok().build();
    }
}
