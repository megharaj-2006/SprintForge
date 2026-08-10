package org.SprintForge.modules.workflow.mapper;

import org.SprintForge.common.config.GlobalMapperConfig;
import org.SprintForge.modules.workflow.dto.*;
import org.SprintForge.modules.workflow.entity.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface WorkflowMapper {

    @Mapping(target = "currentVersionId", source = "currentVersion.id")
    @Mapping(target = "currentVersionNumber", source = "currentVersion.versionNumber")
    WorkflowResponse toWorkflowResponse(Workflow workflow);

    @Mapping(target = "currentVersionNumber", source = "currentVersion.versionNumber")
    WorkflowSummaryResponse toWorkflowSummaryResponse(Workflow workflow);

    @Mapping(target = "workflowId", source = "workflow.id")
    WorkflowVersionResponse toWorkflowVersionResponse(WorkflowVersion version);

    @Mapping(target = "definitionId", source = "definition.id")
    @Mapping(target = "definitionNodeKey", source = "definition.nodeKey")
    WorkflowNodeResponse toWorkflowNodeResponse(WorkflowNode node);

    @Mapping(target = "sourceNodeId", source = "sourceNode.id")
    @Mapping(target = "targetNodeId", source = "targetNode.id")
    WorkflowConnectionResponse toWorkflowConnectionResponse(WorkflowConnection connection);

    WorkflowVariableResponse toWorkflowVariableResponse(WorkflowVariable variable);

    NodeDefinitionResponse toNodeDefinitionResponse(NodeDefinition definition);

    @Mapping(target = "workflowVersionId", source = "workflowVersion.id")
    @Mapping(target = "workflowId", source = "workflowVersion.workflow.id")
    @Mapping(target = "currentNodeId", source = "currentNode.id")
    @Mapping(target = "nodeExecutions", ignore = true)
    WorkflowExecutionResponse toWorkflowExecutionResponse(WorkflowExecution execution);

    @Mapping(target = "workflowExecutionId", source = "workflowExecution.id")
    @Mapping(target = "workflowNodeId", source = "workflowNode.id")
    @Mapping(target = "nodeName", source = "workflowNode.name")
    @Mapping(target = "nodeType", source = "workflowNode.nodeType")
    NodeExecutionResponse toNodeExecutionResponse(NodeExecution nodeExecution);
}
