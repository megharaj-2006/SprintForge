package org.SprintForge.modules.workspace.project.governance.decision.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.governance.decision.dto.request.CreateDecisionRequest;
import org.SprintForge.modules.workspace.project.governance.decision.dto.request.UpdateDecisionRequest;
import org.SprintForge.modules.workspace.project.governance.decision.dto.response.DecisionResponse;
import org.SprintForge.modules.workspace.project.governance.decision.entity.GovernanceDecision;
import org.SprintForge.modules.workspace.project.governance.decision.entity.enums.DecisionStatus;
import org.SprintForge.modules.workspace.project.governance.decision.repository.GovernanceDecisionRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DecisionServiceImpl implements DecisionService {

    private final GovernanceDecisionRepository decisionRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public DecisionResponse createDecision(Long projectId, CreateDecisionRequest request, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        GovernanceDecision decision = new GovernanceDecision();
        decision.setProjectId(projectId);
        decision.setTitle(request.getTitle());
        decision.setProblemStatement(request.getProblemStatement());
        decision.setDecision(request.getDecision());
        decision.setAlternatives(request.getAlternatives());
        decision.setReasoning(request.getReasoning());
        decision.setImpact(request.getImpact());
        decision.setStatus(DecisionStatus.PROPOSED);
        decision.setDecisionDate(request.getDecisionDate() != null ? request.getDecisionDate() : LocalDate.now());
        decision.setOwnerId(request.getOwnerId() != null ? request.getOwnerId() : actorId);

        GovernanceDecision saved = decisionRepository.save(decision);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public DecisionResponse updateDecision(Long decisionId, UpdateDecisionRequest request, Long actorId) {
        GovernanceDecision decision = decisionRepository.findById(decisionId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Decision not found with ID: " + decisionId));

        if (request.getTitle() != null) decision.setTitle(request.getTitle());
        if (request.getProblemStatement() != null) decision.setProblemStatement(request.getProblemStatement());
        if (request.getDecision() != null) decision.setDecision(request.getDecision());
        if (request.getAlternatives() != null) decision.setAlternatives(request.getAlternatives());
        if (request.getReasoning() != null) decision.setReasoning(request.getReasoning());
        if (request.getImpact() != null) decision.setImpact(request.getImpact());
        if (request.getStatus() != null) decision.setStatus(request.getStatus());
        if (request.getOwnerId() != null) decision.setOwnerId(request.getOwnerId());
        if (request.getDecisionDate() != null) decision.setDecisionDate(request.getDecisionDate());

        GovernanceDecision saved = decisionRepository.save(decision);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DecisionResponse> getDecisions(Long projectId) {
        return decisionRepository.findByProjectIdAndIsDeletedFalse(projectId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DecisionResponse getDecision(Long decisionId) {
        GovernanceDecision decision = decisionRepository.findById(decisionId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Decision not found with ID: " + decisionId));
        return toResponse(decision);
    }

    @Override
    @Transactional
    public void deleteDecision(Long decisionId, Long actorId) {
        GovernanceDecision decision = decisionRepository.findById(decisionId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Decision not found with ID: " + decisionId));

        decision.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        decisionRepository.save(decision);
    }

    @Override
    @Transactional
    public DecisionResponse approveDecision(Long decisionId, Long actorId) {
        GovernanceDecision decision = decisionRepository.findById(decisionId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Decision not found with ID: " + decisionId));

        decision.setStatus(DecisionStatus.APPROVED);
        GovernanceDecision saved = decisionRepository.save(decision);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public DecisionResponse supersedeDecision(Long decisionId, Long actorId) {
        GovernanceDecision decision = decisionRepository.findById(decisionId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Decision not found with ID: " + decisionId));

        decision.setStatus(DecisionStatus.SUPERSEDED);
        GovernanceDecision saved = decisionRepository.save(decision);
        return toResponse(saved);
    }

    private DecisionResponse toResponse(GovernanceDecision decision) {
        Long creatorId = null;
        if (decision.getCreatedBy() != null) {
            try {
                creatorId = Long.parseLong(decision.getCreatedBy());
            } catch (NumberFormatException ignored) {}
        }

        return DecisionResponse.builder()
                .id(decision.getId())
                .projectId(decision.getProjectId())
                .title(decision.getTitle())
                .problemStatement(decision.getProblemStatement())
                .decision(decision.getDecision())
                .alternatives(decision.getAlternatives())
                .reasoning(decision.getReasoning())
                .impact(decision.getImpact())
                .status(decision.getStatus())
                .decisionDate(decision.getDecisionDate())
                .ownerId(decision.getOwnerId())
                .createdBy(creatorId)
                .createdAt(decision.getCreatedAt())
                .updatedAt(decision.getUpdatedAt())
                .build();
    }
}
