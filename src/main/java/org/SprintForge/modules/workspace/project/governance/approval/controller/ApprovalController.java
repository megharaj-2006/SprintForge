package org.SprintForge.modules.workspace.project.governance.approval.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.approval.dto.request.DecideApprovalRequest;
import org.SprintForge.modules.workspace.project.governance.approval.dto.request.RequestApprovalRequest;
import org.SprintForge.modules.workspace.project.governance.approval.dto.response.ApprovalResponse;
import org.SprintForge.modules.workspace.project.governance.approval.service.ApprovalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("governanceApprovalController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Approval Controller", description = "REST endpoints for enterprise approval workflows and governance requests")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ApprovalController {

    private final ApprovalService approvalService;

    @Operation(summary = "Request an approval for a release, decision, budget, or requirement")
    @PostMapping("/approvals")
    public ResponseEntity<ApprovalResponse> requestApproval(
            @Valid @RequestBody RequestApprovalRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(approvalService.requestApproval(request, actorId));
    }

    @Operation(summary = "Get all pending approvals")
    @GetMapping("/approvals/pending")
    public ResponseEntity<List<ApprovalResponse>> getPendingApprovals() {
        return ResponseEntity.ok(approvalService.getPendingApprovals());
    }

    @Operation(summary = "Get all approvals for a specific project")
    @GetMapping("/projects/{projectId}/approvals")
    public ResponseEntity<List<ApprovalResponse>> getProjectApprovals(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(approvalService.getProjectApprovals(projectId));
    }

    @Operation(summary = "Get approval details by ID")
    @GetMapping("/approvals/{approvalId}")
    public ResponseEntity<ApprovalResponse> getApproval(@PathVariable("approvalId") Long approvalId) {
        return ResponseEntity.ok(approvalService.getApproval(approvalId));
    }

    @Operation(summary = "Approve a pending request")
    @PatchMapping("/approvals/{approvalId}/approve")
    public ResponseEntity<ApprovalResponse> approve(
            @PathVariable("approvalId") Long approvalId,
            @RequestBody(required = false) DecideApprovalRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(approvalService.approve(approvalId, request, actorId));
    }

    @Operation(summary = "Reject a pending request")
    @PatchMapping("/approvals/{approvalId}/reject")
    public ResponseEntity<ApprovalResponse> reject(
            @PathVariable("approvalId") Long approvalId,
            @RequestBody(required = false) DecideApprovalRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(approvalService.reject(approvalId, request, actorId));
    }

    @Operation(summary = "Cancel an approval request")
    @PostMapping("/approvals/{approvalId}/cancel")
    public ResponseEntity<ApprovalResponse> cancel(
            @PathVariable("approvalId") Long approvalId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(approvalService.cancel(approvalId, actorId));
    }
}
