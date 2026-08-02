package org.SprintForge.modules.workspace.project.governance.risk.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.governance.risk.dto.request.CreateRiskRequest;
import org.SprintForge.modules.workspace.project.governance.risk.dto.request.UpdateRiskRequest;
import org.SprintForge.modules.workspace.project.governance.risk.dto.response.RiskResponse;
import org.SprintForge.modules.workspace.project.governance.risk.entity.GovernanceRisk;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskCategory;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskImpact;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskProbability;
import org.SprintForge.modules.workspace.project.governance.risk.entity.enums.RiskStatus;
import org.SprintForge.modules.workspace.project.governance.risk.repository.GovernanceRiskRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.task.dto.request.CreateTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.entity.enums.TaskPriority;
import org.SprintForge.modules.workspace.task.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiskServiceImpl implements RiskService {

    private final GovernanceRiskRepository riskRepository;
    private final ProjectRepository projectRepository;
    private final TaskService taskService;

    @Override
    @Transactional
    public RiskResponse createRisk(Long projectId, CreateRiskRequest request, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        GovernanceRisk risk = new GovernanceRisk();
        risk.setProjectId(projectId);
        risk.setTitle(request.getTitle());
        risk.setDescription(request.getDescription());
        risk.setCategory(request.getCategory() != null ? request.getCategory() : RiskCategory.TECHNICAL);
        risk.setStatus(RiskStatus.IDENTIFIED);
        risk.setProbability(request.getProbability() != null ? request.getProbability() : RiskProbability.MEDIUM);
        risk.setImpact(request.getImpact() != null ? request.getImpact() : RiskImpact.MEDIUM);
        risk.setOwnerId(request.getOwnerId() != null ? request.getOwnerId() : actorId);
        risk.setIdentifiedDate(LocalDate.now());
        risk.setTargetMitigationDate(request.getTargetMitigationDate());
        risk.setMitigationPlan(request.getMitigationPlan());
        risk.setContingencyPlan(request.getContingencyPlan());
        risk.setTriggerConditions(request.getTriggerConditions());
        risk.setIsArchived(false);

        risk.calculateRiskScore();
        GovernanceRisk saved = riskRepository.save(risk);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public RiskResponse updateRisk(Long riskId, UpdateRiskRequest request, Long actorId) {
        GovernanceRisk risk = riskRepository.findById(riskId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found with ID: " + riskId));

        if (request.getTitle() != null) risk.setTitle(request.getTitle());
        if (request.getDescription() != null) risk.setDescription(request.getDescription());
        if (request.getCategory() != null) risk.setCategory(request.getCategory());
        if (request.getStatus() != null) risk.setStatus(request.getStatus());
        if (request.getProbability() != null) risk.setProbability(request.getProbability());
        if (request.getImpact() != null) risk.setImpact(request.getImpact());
        if (request.getOwnerId() != null) risk.setOwnerId(request.getOwnerId());
        if (request.getTargetMitigationDate() != null) risk.setTargetMitigationDate(request.getTargetMitigationDate());
        if (request.getMitigationPlan() != null) risk.setMitigationPlan(request.getMitigationPlan());
        if (request.getContingencyPlan() != null) risk.setContingencyPlan(request.getContingencyPlan());
        if (request.getTriggerConditions() != null) risk.setTriggerConditions(request.getTriggerConditions());
        if (request.getIsArchived() != null) risk.setIsArchived(request.getIsArchived());

        risk.calculateRiskScore();
        GovernanceRisk saved = riskRepository.save(risk);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RiskResponse> getRisks(Long projectId) {
        return riskRepository.findByProjectIdAndIsDeletedFalse(projectId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RiskResponse getRisk(Long riskId) {
        GovernanceRisk risk = riskRepository.findById(riskId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found with ID: " + riskId));
        return toResponse(risk);
    }

    @Override
    @Transactional
    public void deleteRisk(Long riskId, Long actorId) {
        GovernanceRisk risk = riskRepository.findById(riskId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found with ID: " + riskId));

        risk.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        riskRepository.save(risk);
    }

    @Override
    @Transactional
    public RiskResponse resolveRisk(Long riskId, Long actorId) {
        GovernanceRisk risk = riskRepository.findById(riskId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found with ID: " + riskId));

        risk.setStatus(RiskStatus.RESOLVED);
        risk.setResolvedDate(LocalDateTime.now());
        GovernanceRisk saved = riskRepository.save(risk);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public RiskResponse reopenRisk(Long riskId, Long actorId) {
        GovernanceRisk risk = riskRepository.findById(riskId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found with ID: " + riskId));

        risk.setStatus(RiskStatus.UNDER_REVIEW);
        risk.setResolvedDate(null);
        GovernanceRisk saved = riskRepository.save(risk);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public TaskResponse createMitigationTask(Long riskId, Long actorId) {
        GovernanceRisk risk = riskRepository.findById(riskId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Risk not found with ID: " + riskId));

        CreateTaskRequest request = CreateTaskRequest.builder()
                .projectId(risk.getProjectId())
                .title("[Mitigation] " + risk.getTitle())
                .description("Mitigation task generated for Risk #" + risk.getId() + ": " + (risk.getMitigationPlan() != null ? risk.getMitigationPlan() : risk.getDescription()))
                .priority(risk.getRiskScore() >= 9 ? TaskPriority.URGENT : (risk.getRiskScore() >= 6 ? TaskPriority.HIGH : TaskPriority.MEDIUM))
                .dueDate(risk.getTargetMitigationDate() != null ? risk.getTargetMitigationDate().atStartOfDay() : null)
                .build();

        risk.setStatus(RiskStatus.MITIGATING);
        riskRepository.save(risk);

        return taskService.createTask(request, actorId);
    }

    private RiskResponse toResponse(GovernanceRisk risk) {
        Long creatorId = null;
        if (risk.getCreatedBy() != null) {
            try {
                creatorId = Long.parseLong(risk.getCreatedBy());
            } catch (NumberFormatException ignored) {}
        }

        return RiskResponse.builder()
                .id(risk.getId())
                .projectId(risk.getProjectId())
                .title(risk.getTitle())
                .description(risk.getDescription())
                .category(risk.getCategory())
                .status(risk.getStatus())
                .probability(risk.getProbability())
                .impact(risk.getImpact())
                .severity(risk.getSeverity())
                .riskScore(risk.getRiskScore())
                .ownerId(risk.getOwnerId())
                .identifiedDate(risk.getIdentifiedDate())
                .targetMitigationDate(risk.getTargetMitigationDate())
                .resolvedDate(risk.getResolvedDate())
                .mitigationPlan(risk.getMitigationPlan())
                .contingencyPlan(risk.getContingencyPlan())
                .triggerConditions(risk.getTriggerConditions())
                .isArchived(risk.getIsArchived())
                .createdBy(creatorId)
                .createdAt(risk.getCreatedAt())
                .updatedAt(risk.getUpdatedAt())
                .build();
    }
}
