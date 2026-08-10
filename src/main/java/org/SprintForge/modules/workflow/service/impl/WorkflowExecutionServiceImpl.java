package org.SprintForge.modules.workflow.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workflow.dto.ExecuteWorkflowRequest;
import org.SprintForge.modules.workflow.dto.WorkflowExecutionResponse;
import org.SprintForge.modules.workflow.entity.*;
import org.SprintForge.modules.workflow.entity.enums.ExecutionStatus;
import org.SprintForge.modules.workflow.mapper.WorkflowMapper;
import org.SprintForge.modules.workflow.repository.*;
import org.SprintForge.modules.workflow.runtime.WorkflowRuntime;
import org.SprintForge.modules.workflow.service.WorkflowExecutionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowExecutionServiceImpl implements WorkflowExecutionService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowVersionRepository workflowVersionRepository;
    private final WorkflowExecutionRepository executionRepository;
    private final NodeExecutionRepository nodeExecutionRepository;
    private final WorkflowRuntime workflowRuntime;
    private final WorkflowMapper workflowMapper;

    @Override
    @Transactional
    public WorkflowExecutionResponse executeWorkflow(Long workflowId, ExecuteWorkflowRequest request) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found with id: " + workflowId));

        WorkflowVersion versionToExecute;
        if (request != null && request.getVersionId() != null) {
            versionToExecute = workflowVersionRepository.findById(request.getVersionId())
                    .orElseThrow(() -> new ResourceNotFoundException("WorkflowVersion not found with id: " + request.getVersionId()));
        } else {
            versionToExecute = workflow.getCurrentVersion();
            if (versionToExecute == null) {
                throw new BusinessException("Workflow has no active version to execute");
            }
        }

        String triggeredBy = (request != null && request.getTriggeredBy() != null) ? request.getTriggeredBy() : "API";
        String triggerSource = (request != null && request.getTriggerSource() != null) ? request.getTriggerSource() : "REST";

        WorkflowExecution execution = workflowRuntime.runWorkflow(
                versionToExecute,
                triggeredBy,
                triggerSource,
                request != null ? request.getInputVariables() : null
        );

        return buildExecutionResponse(execution);
    }

    @Override
    @Transactional
    public WorkflowExecutionResponse cancelExecution(Long executionId) {
        WorkflowExecution execution = getExecutionEntity(executionId);
        execution.setStatus(ExecutionStatus.CANCELLED);
        WorkflowExecution updated = executionRepository.save(execution);
        return buildExecutionResponse(updated);
    }

    @Override
    @Transactional
    public WorkflowExecutionResponse retryExecution(Long executionId) {
        WorkflowExecution execution = getExecutionEntity(executionId);
        ExecuteWorkflowRequest request = new ExecuteWorkflowRequest();
        request.setVersionId(execution.getWorkflowVersion().getId());
        request.setTriggeredBy(execution.getTriggeredBy());
        request.setTriggerSource("RETRY");
        return executeWorkflow(execution.getWorkflowVersion().getWorkflow().getId(), request);
    }

    @Override
    @Transactional
    public WorkflowExecutionResponse resumeExecution(Long executionId) {
        WorkflowExecution execution = getExecutionEntity(executionId);
        execution.setStatus(ExecutionStatus.RUNNING);
        WorkflowExecution updated = executionRepository.save(execution);
        return buildExecutionResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowExecutionResponse getExecutionById(Long executionId) {
        WorkflowExecution execution = getExecutionEntity(executionId);
        return buildExecutionResponse(execution);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkflowExecutionResponse> getExecutionHistory(Long workflowId, Pageable pageable) {
        return executionRepository.findByWorkflowVersionWorkflowId(workflowId, pageable)
                .map(this::buildExecutionResponse);
    }

    private WorkflowExecution getExecutionEntity(Long executionId) {
        return executionRepository.findById(executionId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkflowExecution not found with id: " + executionId));
    }

    private WorkflowExecutionResponse buildExecutionResponse(WorkflowExecution execution) {
        WorkflowExecutionResponse response = workflowMapper.toWorkflowExecutionResponse(execution);
        List<NodeExecution> nodeExecutions = nodeExecutionRepository.findByWorkflowExecutionId(execution.getId());
        response.setNodeExecutions(nodeExecutions.stream().map(workflowMapper::toNodeExecutionResponse).toList());
        return response;
    }
}
