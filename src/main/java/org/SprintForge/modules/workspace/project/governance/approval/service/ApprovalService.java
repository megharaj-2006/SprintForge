package org.SprintForge.modules.workspace.project.governance.approval.service;

import org.SprintForge.modules.workspace.project.governance.approval.dto.request.DecideApprovalRequest;
import org.SprintForge.modules.workspace.project.governance.approval.dto.request.RequestApprovalRequest;
import org.SprintForge.modules.workspace.project.governance.approval.dto.response.ApprovalResponse;

import java.util.List;

public interface ApprovalService {
    ApprovalResponse requestApproval(RequestApprovalRequest request, Long actorId);
    List<ApprovalResponse> getPendingApprovals();
    List<ApprovalResponse> getProjectApprovals(Long projectId);
    ApprovalResponse getApproval(Long approvalId);
    ApprovalResponse approve(Long approvalId, DecideApprovalRequest request, Long actorId);
    ApprovalResponse reject(Long approvalId, DecideApprovalRequest request, Long actorId);
    ApprovalResponse cancel(Long approvalId, Long actorId);
}
