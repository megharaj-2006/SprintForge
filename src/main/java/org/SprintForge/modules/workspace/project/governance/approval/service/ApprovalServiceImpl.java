package org.SprintForge.modules.workspace.project.governance.approval.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.governance.approval.dto.request.DecideApprovalRequest;
import org.SprintForge.modules.workspace.project.governance.approval.dto.request.RequestApprovalRequest;
import org.SprintForge.modules.workspace.project.governance.approval.dto.response.ApprovalResponse;
import org.SprintForge.modules.workspace.project.governance.approval.entity.GovernanceApproval;
import org.SprintForge.modules.workspace.project.governance.approval.entity.enums.ApprovalStatus;
import org.SprintForge.modules.workspace.project.governance.approval.repository.GovernanceApprovalRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {

    private final GovernanceApprovalRepository approvalRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public ApprovalResponse requestApproval(RequestApprovalRequest request, Long actorId) {
        Project project = projectRepository.findById(request.getProjectId())
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + request.getProjectId()));

        GovernanceApproval approval = new GovernanceApproval();
        approval.setProjectId(request.getProjectId());
        approval.setEntityType(request.getEntityType());
        approval.setEntityId(request.getEntityId());
        approval.setTitle(request.getTitle());
        approval.setStatus(ApprovalStatus.PENDING);
        approval.setRequestedById(actorId);
        approval.setRequestedAt(LocalDateTime.now());
        approval.setComments(request.getComments());

        GovernanceApproval saved = approvalRepository.save(approval);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApprovalResponse> getPendingApprovals() {
        return approvalRepository.findByStatusAndIsDeletedFalse(ApprovalStatus.PENDING)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApprovalResponse> getProjectApprovals(Long projectId) {
        return approvalRepository.findByProjectIdAndIsDeletedFalse(projectId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ApprovalResponse getApproval(Long approvalId) {
        GovernanceApproval approval = approvalRepository.findById(approvalId)
                .filter(a -> !a.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Approval not found with ID: " + approvalId));
        return toResponse(approval);
    }

    @Override
    @Transactional
    public ApprovalResponse approve(Long approvalId, DecideApprovalRequest request, Long actorId) {
        GovernanceApproval approval = approvalRepository.findById(approvalId)
                .filter(a -> !a.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Approval not found with ID: " + approvalId));

        approval.setStatus(ApprovalStatus.APPROVED);
        approval.setApprovedById(actorId);
        approval.setApprovedAt(LocalDateTime.now());
        if (request != null && request.getComments() != null) {
            approval.setComments(request.getComments());
        }

        GovernanceApproval saved = approvalRepository.save(approval);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ApprovalResponse reject(Long approvalId, DecideApprovalRequest request, Long actorId) {
        GovernanceApproval approval = approvalRepository.findById(approvalId)
                .filter(a -> !a.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Approval not found with ID: " + approvalId));

        approval.setStatus(ApprovalStatus.REJECTED);
        approval.setApprovedById(actorId);
        approval.setApprovedAt(LocalDateTime.now());
        if (request != null && request.getComments() != null) {
            approval.setComments(request.getComments());
        }

        GovernanceApproval saved = approvalRepository.save(approval);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ApprovalResponse cancel(Long approvalId, Long actorId) {
        GovernanceApproval approval = approvalRepository.findById(approvalId)
                .filter(a -> !a.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Approval not found with ID: " + approvalId));

        approval.setStatus(ApprovalStatus.CANCELLED);
        GovernanceApproval saved = approvalRepository.save(approval);
        return toResponse(saved);
    }

    private ApprovalResponse toResponse(GovernanceApproval approval) {
        Long creatorId = null;
        if (approval.getCreatedBy() != null) {
            try {
                creatorId = Long.parseLong(approval.getCreatedBy());
            } catch (NumberFormatException ignored) {}
        }

        return ApprovalResponse.builder()
                .id(approval.getId())
                .projectId(approval.getProjectId())
                .entityType(approval.getEntityType())
                .entityId(approval.getEntityId())
                .title(approval.getTitle())
                .status(approval.getStatus())
                .requestedById(approval.getRequestedById())
                .requestedAt(approval.getRequestedAt())
                .approvedById(approval.getApprovedById())
                .approvedAt(approval.getApprovedAt())
                .comments(approval.getComments())
                .createdBy(creatorId)
                .createdAt(approval.getCreatedAt())
                .updatedAt(approval.getUpdatedAt())
                .build();
    }
}
