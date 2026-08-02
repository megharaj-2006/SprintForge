package org.SprintForge.modules.workspace.project.governance.risk.service;

import org.SprintForge.modules.workspace.project.governance.risk.dto.request.CreateRiskRequest;
import org.SprintForge.modules.workspace.project.governance.risk.dto.request.UpdateRiskRequest;
import org.SprintForge.modules.workspace.project.governance.risk.dto.response.RiskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;

import java.util.List;

public interface RiskService {
    RiskResponse createRisk(Long projectId, CreateRiskRequest request, Long actorId);
    RiskResponse updateRisk(Long riskId, UpdateRiskRequest request, Long actorId);
    List<RiskResponse> getRisks(Long projectId);
    RiskResponse getRisk(Long riskId);
    void deleteRisk(Long riskId, Long actorId);
    RiskResponse resolveRisk(Long riskId, Long actorId);
    RiskResponse reopenRisk(Long riskId, Long actorId);
    TaskResponse createMitigationTask(Long riskId, Long actorId);
}
