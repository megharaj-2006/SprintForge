package org.SprintForge.modules.workflow.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workflow.dto.*;
import org.SprintForge.modules.workflow.entity.*;
import org.SprintForge.modules.workflow.entity.enums.WorkflowStatus;
import org.SprintForge.modules.workflow.event.*;
import org.SprintForge.modules.workflow.mapper.WorkflowMapper;
import org.SprintForge.modules.workflow.repository.NodeDefinitionRepository;
import org.SprintForge.modules.workflow.repository.WorkflowRepository;
import org.SprintForge.modules.workflow.repository.WorkflowVersionRepository;
import org.SprintForge.modules.workflow.service.WorkflowManagementService;
import org.SprintForge.modules.workflow.validation.WorkflowValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowManagementServiceImpl implements WorkflowManagementService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowVersionRepository workflowVersionRepository;
    private final NodeDefinitionRepository nodeDefinitionRepository;
    private final WorkflowMapper workflowMapper;
    private final WorkflowValidator workflowValidator;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public WorkflowResponse createWorkflow(CreateWorkflowRequest request) {
        log.info("Creating new workflow: {}", request.getName());

        Workflow workflow = new Workflow();
        workflow.setWorkspaceId(request.getWorkspaceId());
        workflow.setName(request.getName());
        workflow.setDescription(request.getDescription());
        workflow.setCategory(request.getCategory());
        workflow.setStatus(WorkflowStatus.DRAFT);
        workflow.setEnabled(true);
        workflow.setPublished(false);
        workflow.setArchived(false);

        Workflow savedWorkflow = workflowRepository.save(workflow);

        // Initial draft version v1
        WorkflowVersion initialVersion = new WorkflowVersion();
        initialVersion.setWorkflow(savedWorkflow);
        initialVersion.setVersionNumber(1);
        initialVersion.setPublished(false);
        initialVersion.setChangeSummary("Initial draft version");

        WorkflowVersion savedVersion = workflowVersionRepository.save(initialVersion);
        savedWorkflow.setCurrentVersion(savedVersion);
        savedWorkflow.getVersions().add(savedVersion);

        // Create default start node (manual.trigger)
        nodeDefinitionRepository.findByNodeKey("manual.trigger").ifPresent(def -> {
            WorkflowNode startNode = new WorkflowNode();
            startNode.setWorkflowVersion(savedVersion);
            startNode.setDefinition(def);
            startNode.setName("Start");
            startNode.setNodeType("TRIGGER");
            startNode.setPositionX(100.0);
            startNode.setPositionY(100.0);
            savedVersion.getNodes().add(startNode);
        });

        Workflow updated = workflowRepository.save(savedWorkflow);
        eventPublisher.publishEvent(new WorkflowCreatedEvent(updated.getId(), updated.getWorkspaceId(), updated.getName(), updated.getCreatedBy()));

        return workflowMapper.toWorkflowResponse(updated);
    }

    @Override
    @Transactional
    public WorkflowResponse updateWorkflow(Long id, UpdateWorkflowRequest request) {
        Workflow workflow = getEntityById(id);

        if (request.getName() != null) workflow.setName(request.getName());
        if (request.getDescription() != null) workflow.setDescription(request.getDescription());
        if (request.getCategory() != null) workflow.setCategory(request.getCategory());
        if (request.getStatus() != null) workflow.setStatus(request.getStatus());
        if (request.getEnabled() != null) workflow.setEnabled(request.getEnabled());

        Workflow updated = workflowRepository.save(workflow);
        eventPublisher.publishEvent(new WorkflowUpdatedEvent(updated.getId(), updated.getName(), updated.getUpdatedBy()));

        return workflowMapper.toWorkflowResponse(updated);
    }

    @Override
    @Transactional
    public WorkflowResponse archiveWorkflow(Long id) {
        Workflow workflow = getEntityById(id);
        workflow.setArchived(true);
        workflow.setStatus(WorkflowStatus.ARCHIVED);
        Workflow updated = workflowRepository.save(workflow);

        eventPublisher.publishEvent(new WorkflowArchivedEvent(updated.getId(), updated.getUpdatedBy()));
        return workflowMapper.toWorkflowResponse(updated);
    }

    @Override
    @Transactional
    public WorkflowResponse restoreWorkflow(Long id) {
        Workflow workflow = workflowRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found with id: " + id));
        workflow.setArchived(false);
        workflow.setStatus(WorkflowStatus.DRAFT);
        Workflow updated = workflowRepository.save(workflow);

        return workflowMapper.toWorkflowResponse(updated);
    }

    @Override
    @Transactional
    public WorkflowResponse cloneWorkflow(Long id, CloneWorkflowRequest request) {
        Workflow source = getEntityById(id);

        CreateWorkflowRequest createRequest = new CreateWorkflowRequest();
        createRequest.setName(request.getNewName());
        createRequest.setDescription("Cloned from workflow #" + source.getId());
        createRequest.setCategory(source.getCategory());
        createRequest.setWorkspaceId(request.getTargetWorkspaceId() != null ? request.getTargetWorkspaceId() : source.getWorkspaceId());

        return createWorkflow(createRequest);
    }

    @Override
    @Transactional
    public WorkflowResponse publishWorkflow(Long id, PublishWorkflowRequest request) {
        Workflow workflow = getEntityById(id);
        WorkflowVersion currentVersion = workflow.getCurrentVersion();

        if (currentVersion == null) {
            throw new BusinessException("Workflow has no active version to publish");
        }

        WorkflowValidationResponse validation = workflowValidator.validate(workflow, currentVersion);
        if (!validation.isValid()) {
            eventPublisher.publishEvent(new WorkflowValidationFailedEvent(id, currentVersion.getId(),
                    validation.getErrors().stream().map(e -> e.getMessage()).toList()));
            throw new BusinessException("Cannot publish invalid workflow: " + validation.getErrors());
        }

        currentVersion.setPublished(true);
        currentVersion.setPublishedAt(LocalDateTime.now());
        if (request != null && request.getChangeSummary() != null) {
            currentVersion.setChangeSummary(request.getChangeSummary());
        }
        workflowVersionRepository.save(currentVersion);

        workflow.setPublished(true);
        workflow.setStatus(WorkflowStatus.ACTIVE);
        Workflow updated = workflowRepository.save(workflow);

        eventPublisher.publishEvent(new WorkflowPublishedEvent(updated.getId(), currentVersion.getId(), currentVersion.getVersionNumber(), updated.getUpdatedBy()));

        return workflowMapper.toWorkflowResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowResponse getWorkflowById(Long id) {
        return workflowMapper.toWorkflowResponse(getEntityById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkflowSummaryResponse> getWorkflows(Long workspaceId, Pageable pageable) {
        Page<Workflow> page = workspaceId != null ?
                workflowRepository.findByWorkspaceIdAndArchivedFalse(workspaceId, pageable) :
                workflowRepository.findByArchivedFalse(pageable);
        return page.map(workflowMapper::toWorkflowSummaryResponse);
    }

    @Override
    @Transactional
    public void deleteWorkflow(Long id) {
        Workflow workflow = getEntityById(id);
        workflowRepository.delete(workflow);
    }

    private Workflow getEntityById(Long id) {
        return workflowRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found with id: " + id));
    }
}
