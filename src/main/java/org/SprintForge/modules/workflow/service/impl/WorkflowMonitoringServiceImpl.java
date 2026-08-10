package org.SprintForge.modules.workflow.service.impl;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workflow.dto.WorkflowMonitoringResponse;
import org.SprintForge.modules.workflow.dto.WorkflowSummaryResponse;
import org.SprintForge.modules.workflow.entity.Workflow;
import org.SprintForge.modules.workflow.entity.enums.ExecutionStatus;
import org.SprintForge.modules.workflow.mapper.WorkflowMapper;
import org.SprintForge.modules.workflow.repository.WorkflowExecutionRepository;
import org.SprintForge.modules.workflow.repository.WorkflowRepository;
import org.SprintForge.modules.workflow.service.WorkflowExecutionService;
import org.SprintForge.modules.workflow.service.WorkflowMonitoringService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkflowMonitoringServiceImpl implements WorkflowMonitoringService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowExecutionRepository executionRepository;
    private final WorkflowExecutionService executionService;
    private final WorkflowMapper workflowMapper;

    @Override
    @Transactional(readOnly = true)
    public WorkflowMonitoringResponse getWorkflowMonitoring(Long workflowId) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found with id: " + workflowId));

        long total = executionRepository.countByWorkflowId(workflowId);
        long success = executionRepository.countByWorkflowIdAndStatus(workflowId, ExecutionStatus.COMPLETED);
        long failed = executionRepository.countByWorkflowIdAndStatus(workflowId, ExecutionStatus.FAILED);
        Double avgDuration = executionRepository.findAverageDurationByWorkflowId(workflowId);

        WorkflowMonitoringResponse response = new WorkflowMonitoringResponse();
        response.setWorkflowId(workflowId);
        response.setTotalExecutions(total);
        response.setSuccessfulExecutions(success);
        response.setFailedExecutions(failed);
        response.setSuccessRate(total > 0 ? ((double) success / total) * 100.0 : 0.0);
        response.setFailureRate(total > 0 ? ((double) failed / total) * 100.0 : 0.0);
        response.setAverageExecutionTimeMillis(avgDuration != null ? avgDuration : 0.0);

        var historyPage = executionService.getExecutionHistory(workflowId, PageRequest.of(0, 10));
        response.setRecentExecutions(historyPage.getContent());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowSummaryResponse> getMostExecutedWorkflows(int limit) {
        List<Object[]> results = executionRepository.findMostExecutedWorkflows(PageRequest.of(0, limit));
        List<WorkflowSummaryResponse> summaries = new ArrayList<>();
        for (Object[] row : results) {
            Long workflowId = (Long) row[0];
            workflowRepository.findById(workflowId)
                    .ifPresent(w -> summaries.add(workflowMapper.toWorkflowSummaryResponse(w)));
        }
        return summaries;
    }
}
