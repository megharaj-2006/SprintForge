package org.SprintForge.modules.workflow.controller;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workflow.dto.WorkflowExecutionResponse;
import org.SprintForge.modules.workflow.dto.WorkflowMonitoringResponse;
import org.SprintForge.modules.workflow.service.WorkflowExecutionService;
import org.SprintForge.modules.workflow.service.WorkflowMonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow-executions")
@RequiredArgsConstructor
public class WorkflowExecutionController {

    private final WorkflowExecutionService workflowExecutionService;
    private final WorkflowMonitoringService workflowMonitoringService;

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowExecutionResponse> getExecutionById(@PathVariable Long id) {
        return ResponseEntity.ok(workflowExecutionService.getExecutionById(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<WorkflowExecutionResponse> cancelExecution(@PathVariable Long id) {
        return ResponseEntity.ok(workflowExecutionService.cancelExecution(id));
    }

    @PostMapping("/{id}/retry")
    public ResponseEntity<WorkflowExecutionResponse> retryExecution(@PathVariable Long id) {
        return ResponseEntity.ok(workflowExecutionService.retryExecution(id));
    }

    @GetMapping("/monitoring/{workflowId}")
    public ResponseEntity<WorkflowMonitoringResponse> getMonitoringData(@PathVariable Long workflowId) {
        return ResponseEntity.ok(workflowMonitoringService.getWorkflowMonitoring(workflowId));
    }
}
