package org.SprintForge.modules.workspace.project.governance.decision.service;

import org.SprintForge.modules.workspace.project.governance.decision.dto.request.CreateDecisionRequest;
import org.SprintForge.modules.workspace.project.governance.decision.dto.request.UpdateDecisionRequest;
import org.SprintForge.modules.workspace.project.governance.decision.dto.response.DecisionResponse;

import java.util.List;

public interface DecisionService {
    DecisionResponse createDecision(Long projectId, CreateDecisionRequest request, Long actorId);
    DecisionResponse updateDecision(Long decisionId, UpdateDecisionRequest request, Long actorId);
    List<DecisionResponse> getDecisions(Long projectId);
    DecisionResponse getDecision(Long decisionId);
    void deleteDecision(Long decisionId, Long actorId);
    DecisionResponse approveDecision(Long decisionId, Long actorId);
    DecisionResponse supersedeDecision(Long decisionId, Long actorId);
}
