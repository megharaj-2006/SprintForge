package org.SprintForge.modules.workflow.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workflow.dto.*;
import org.SprintForge.modules.workflow.entity.*;
import org.SprintForge.modules.workflow.event.WorkflowVersionCreatedEvent;
import org.SprintForge.modules.workflow.mapper.WorkflowMapper;
import org.SprintForge.modules.workflow.repository.*;
import org.SprintForge.modules.workflow.service.WorkflowVersionService;
import org.SprintForge.modules.workflow.validation.VersionValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowVersionServiceImpl implements WorkflowVersionService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowVersionRepository workflowVersionRepository;
    private final NodeDefinitionRepository nodeDefinitionRepository;
    private final WorkflowNodeRepository workflowNodeRepository;
    private final WorkflowConnectionRepository workflowConnectionRepository;
    private final WorkflowVariableRepository workflowVariableRepository;
    private final WorkflowMapper workflowMapper;
    private final VersionValidator versionValidator;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public WorkflowVersionResponse createVersion(Long workflowId, String changeSummary) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found with id: " + workflowId));

        int nextVersionNumber = workflowVersionRepository.findMaxVersionNumberByWorkflowId(workflowId).orElse(0) + 1;

        WorkflowVersion newVersion = new WorkflowVersion();
        newVersion.setWorkflow(workflow);
        newVersion.setVersionNumber(nextVersionNumber);
        newVersion.setPublished(false);
        newVersion.setChangeSummary(changeSummary != null ? changeSummary : "New draft version v" + nextVersionNumber);

        WorkflowVersion savedVersion = workflowVersionRepository.save(newVersion);
        workflow.setCurrentVersion(savedVersion);
        workflowRepository.save(workflow);

        eventPublisher.publishEvent(new WorkflowVersionCreatedEvent(workflowId, savedVersion.getId(), nextVersionNumber, savedVersion.getCreatedBy()));

        return workflowMapper.toWorkflowVersionResponse(savedVersion);
    }

    @Override
    @Transactional
    public WorkflowVersionResponse publishVersion(Long versionId, String changeSummary) {
        WorkflowVersion version = getVersionEntity(versionId);
        version.setPublished(true);
        version.setPublishedAt(LocalDateTime.now());
        if (changeSummary != null) {
            version.setChangeSummary(changeSummary);
        }
        return workflowMapper.toWorkflowVersionResponse(workflowVersionRepository.save(version));
    }

    @Override
    @Transactional
    public WorkflowVersionResponse rollbackVersion(Long workflowId, Integer targetVersionNumber) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found with id: " + workflowId));

        WorkflowVersion targetVersion = workflowVersionRepository.findByWorkflowIdAndVersionNumber(workflowId, targetVersionNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow version " + targetVersionNumber + " not found"));

        workflow.setCurrentVersion(targetVersion);
        workflowRepository.save(workflow);

        return workflowMapper.toWorkflowVersionResponse(targetVersion);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowVersionResponse getVersionById(Long versionId) {
        return workflowMapper.toWorkflowVersionResponse(getVersionEntity(versionId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkflowVersionResponse> getVersionsByWorkflowId(Long workflowId) {
        return workflowVersionRepository.findByWorkflowIdOrderByVersionNumberDesc(workflowId)
                .stream().map(workflowMapper::toWorkflowVersionResponse).toList();
    }

    @Override
    @Transactional
    public WorkflowVersionResponse addNode(Long versionId, CreateNodeRequest request) {
        WorkflowVersion targetVersion = getMutableVersion(versionId);

        NodeDefinition definition = nodeDefinitionRepository.findById(request.getDefinitionId())
                .orElseThrow(() -> new ResourceNotFoundException("NodeDefinition not found with id: " + request.getDefinitionId()));

        WorkflowNode node = new WorkflowNode();
        node.setWorkflowVersion(targetVersion);
        node.setDefinition(definition);
        node.setName(request.getName());
        node.setNodeType(request.getNodeType());
        node.setPositionX(request.getPositionX());
        node.setPositionY(request.getPositionY());
        node.setEnabled(request.getEnabled() != null ? request.getEnabled() : true);

        targetVersion.getNodes().add(node);
        WorkflowVersion updated = workflowVersionRepository.save(targetVersion);
        return workflowMapper.toWorkflowVersionResponse(updated);
    }

    @Override
    @Transactional
    public WorkflowVersionResponse addConnection(Long versionId, CreateConnectionRequest request) {
        WorkflowVersion targetVersion = getMutableVersion(versionId);

        WorkflowNode source = workflowNodeRepository.findById(request.getSourceNodeId())
                .orElseThrow(() -> new ResourceNotFoundException("Source node not found"));
        WorkflowNode target = workflowNodeRepository.findById(request.getTargetNodeId())
                .orElseThrow(() -> new ResourceNotFoundException("Target node not found"));

        WorkflowConnection connection = new WorkflowConnection();
        connection.setWorkflowVersion(targetVersion);
        connection.setSourceNode(source);
        connection.setTargetNode(target);
        connection.setBranchLabel(request.getBranchLabel());
        connection.setPriority(request.getPriority());
        connection.setConditionExpression(request.getConditionExpression());

        targetVersion.getConnections().add(connection);
        WorkflowVersion updated = workflowVersionRepository.save(targetVersion);
        return workflowMapper.toWorkflowVersionResponse(updated);
    }

    @Override
    @Transactional
    public WorkflowVersionResponse addVariable(Long versionId, CreateVariableRequest request) {
        WorkflowVersion targetVersion = getMutableVersion(versionId);

        WorkflowVariable variable = new WorkflowVariable();
        variable.setWorkflowVersion(targetVersion);
        variable.setName(request.getName());
        variable.setType(request.getType());
        variable.setScope(request.getScope());
        variable.setDefaultValue(request.getDefaultValue());
        variable.setRequired(request.getRequired());

        targetVersion.getVariables().add(variable);
        WorkflowVersion updated = workflowVersionRepository.save(targetVersion);
        return workflowMapper.toWorkflowVersionResponse(updated);
    }

    private WorkflowVersion getMutableVersion(Long versionId) {
        WorkflowVersion version = getVersionEntity(versionId);
        if (Boolean.TRUE.equals(version.getPublished())) {
            log.info("Version ID {} is published. Auto-creating a new draft version for modifications.", versionId);
            WorkflowVersionResponse newDraft = createVersion(version.getWorkflow().getId(), "Auto-created draft from published version v" + version.getVersionNumber());
            return getVersionEntity(newDraft.getId());
        }
        return version;
    }

    private WorkflowVersion getVersionEntity(Long versionId) {
        return workflowVersionRepository.findById(versionId)
                .orElseThrow(() -> new ResourceNotFoundException("WorkflowVersion not found with id: " + versionId));
    }
}
