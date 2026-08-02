package org.SprintForge.modules.workspace.project.governance.change.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.governance.change.dto.request.CreateChangeRequest;
import org.SprintForge.modules.workspace.project.governance.change.dto.request.UpdateChangeRequest;
import org.SprintForge.modules.workspace.project.governance.change.dto.response.ChangeResponse;
import org.SprintForge.modules.workspace.project.governance.change.entity.GovernanceChange;
import org.SprintForge.modules.workspace.project.governance.change.entity.enums.ChangeStatus;
import org.SprintForge.modules.workspace.project.governance.change.entity.enums.ChangeType;
import org.SprintForge.modules.workspace.project.governance.change.repository.GovernanceChangeRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectChangeServiceImpl implements ProjectChangeService {

    private final GovernanceChangeRepository changeRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public ChangeResponse createChangeRequest(Long projectId, CreateChangeRequest request, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        GovernanceChange change = new GovernanceChange();
        change.setProjectId(projectId);
        change.setChangeType(request.getChangeType() != null ? request.getChangeType() : ChangeType.SCOPE);
        change.setTitle(request.getTitle());
        change.setDescription(request.getDescription());
        change.setReason(request.getReason());
        change.setImpact(request.getImpact());
        change.setRequestedById(actorId);
        change.setStatus(ChangeStatus.REQUESTED);

        GovernanceChange saved = changeRepository.save(change);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ChangeResponse updateChangeRequest(Long changeId, UpdateChangeRequest request, Long actorId) {
        GovernanceChange change = changeRepository.findById(changeId)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Change request not found with ID: " + changeId));

        if (request.getTitle() != null) change.setTitle(request.getTitle());
        if (request.getChangeType() != null) change.setChangeType(request.getChangeType());
        if (request.getDescription() != null) change.setDescription(request.getDescription());
        if (request.getReason() != null) change.setReason(request.getReason());
        if (request.getImpact() != null) change.setImpact(request.getImpact());
        if (request.getApprovedById() != null) change.setApprovedById(request.getApprovedById());
        if (request.getImplementedById() != null) change.setImplementedById(request.getImplementedById());

        if (request.getStatus() != null) {
            change.setStatus(request.getStatus());
            if (request.getStatus() == ChangeStatus.IMPLEMENTED || request.getStatus() == ChangeStatus.ROLLED_BACK) {
                change.setCompletedAt(LocalDateTime.now());
            }
        }

        GovernanceChange saved = changeRepository.save(change);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChangeResponse> getProjectChanges(Long projectId) {
        return changeRepository.findByProjectIdAndIsDeletedFalse(projectId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ChangeResponse getChange(Long changeId) {
        GovernanceChange change = changeRepository.findById(changeId)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Change request not found with ID: " + changeId));
        return toResponse(change);
    }

    @Override
    @Transactional
    public void deleteChange(Long changeId, Long actorId) {
        GovernanceChange change = changeRepository.findById(changeId)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Change request not found with ID: " + changeId));

        change.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        changeRepository.save(change);
    }

    private ChangeResponse toResponse(GovernanceChange change) {
        Long creatorId = null;
        if (change.getCreatedBy() != null) {
            try {
                creatorId = Long.parseLong(change.getCreatedBy());
            } catch (NumberFormatException ignored) {}
        }

        return ChangeResponse.builder()
                .id(change.getId())
                .projectId(change.getProjectId())
                .changeType(change.getChangeType())
                .title(change.getTitle())
                .description(change.getDescription())
                .reason(change.getReason())
                .impact(change.getImpact())
                .requestedById(change.getRequestedById())
                .approvedById(change.getApprovedById())
                .implementedById(change.getImplementedById())
                .status(change.getStatus())
                .completedAt(change.getCompletedAt())
                .createdBy(creatorId)
                .createdAt(change.getCreatedAt())
                .updatedAt(change.getUpdatedAt())
                .build();
    }
}
