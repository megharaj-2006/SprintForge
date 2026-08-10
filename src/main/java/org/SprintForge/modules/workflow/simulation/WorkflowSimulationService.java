package org.SprintForge.modules.workflow.simulation;

public interface WorkflowSimulationService {
    SimulationResult runSimulation(SimulationContext context);
    SimulationResult getSimulationStatus(String simulationId);
    SimulationResult replaySimulation(String simulationId);
    void pauseExecution(String executionId);
    void resumeExecution(String executionId);
}
