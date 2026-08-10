package org.SprintForge.modules.workflow.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workflow.dto.*;
import org.SprintForge.modules.workflow.service.WorkflowExecutionService;
import org.SprintForge.modules.workflow.service.WorkflowManagementService;
import org.SprintForge.modules.workflow.service.WorkflowValidationService;
import org.SprintForge.modules.workflow.service.WorkflowVersionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workflows")
@RequiredArgsConstructor
public class WorkflowController {

    private final WorkflowManagementService workflowManagementService;
    private final WorkflowVersionService workflowVersionService;
    private final WorkflowExecutionService workflowExecutionService;
    private final WorkflowValidationService workflowValidationService;

    @PostMapping
    public ResponseEntity<WorkflowResponse> createWorkflow(@Valid @RequestBody CreateWorkflowRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workflowManagementService.createWorkflow(request));
    }

    @GetMapping
    public ResponseEntity<Page<WorkflowSummaryResponse>> getWorkflows(
            @RequestParam(required = false) Long workspaceId,
            Pageable pageable) {
        return ResponseEntity.ok(workflowManagementService.getWorkflows(workspaceId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowResponse> getWorkflowById(@PathVariable Long id) {
        return ResponseEntity.ok(workflowManagementService.getWorkflowById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WorkflowResponse> updateWorkflow(
            @PathVariable Long id,
            @Valid @RequestBody UpdateWorkflowRequest request) {
        return ResponseEntity.ok(workflowManagementService.updateWorkflow(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkflow(@PathVariable Long id) {
        workflowManagementService.deleteWorkflow(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<WorkflowResponse> publishWorkflow(
            @PathVariable Long id,
            @RequestBody(required = false) PublishWorkflowRequest request) {
        return ResponseEntity.ok(workflowManagementService.publishWorkflow(id, request));
    }

    @PostMapping("/{id}/execute")
    public ResponseEntity<WorkflowExecutionResponse> executeWorkflow(
            @PathVariable Long id,
            @RequestBody(required = false) ExecuteWorkflowRequest request) {
        return ResponseEntity.ok(workflowExecutionService.executeWorkflow(id, request));
    }

    @PostMapping("/{id}/validate")
    public ResponseEntity<WorkflowValidationResponse> validateWorkflow(
            @PathVariable Long id,
            @RequestParam(required = false) Long versionId) {
        return ResponseEntity.ok(workflowValidationService.validateWorkflow(id, versionId));
    }

    @GetMapping("/{id}/versions")
    public ResponseEntity<List<WorkflowVersionResponse>> getWorkflowVersions(@PathVariable Long id) {
        return ResponseEntity.ok(workflowVersionService.getVersionsByWorkflowId(id));
    }
}
