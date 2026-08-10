package org.SprintForge.modules.workflow.validation;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessException;
import org.SprintForge.modules.workflow.dto.WorkflowValidationResponse;
import org.SprintForge.modules.workflow.entity.WorkflowConnection;
import org.SprintForge.modules.workflow.entity.WorkflowNode;
import org.SprintForge.modules.workflow.entity.WorkflowVersion;
import org.SprintForge.modules.workflow.graph.GraphBuilder;
import org.SprintForge.modules.workflow.graph.GraphValidator;
import org.SprintForge.modules.workflow.graph.WorkflowGraph;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VersionValidator {

    private final GraphBuilder graphBuilder;
    private final GraphValidator graphValidator;
    private final NodeValidator nodeValidator;
    private final ConnectionValidator connectionValidator;
    private final VariableValidator variableValidator;

    public void validatePublishedImmutability(WorkflowVersion version) {
        if (Boolean.TRUE.equals(version.getPublished())) {
            throw new BusinessException("Published workflow version (v" + version.getVersionNumber() + ") cannot be modified.");
        }
    }

    public WorkflowValidationResponse validateVersion(WorkflowVersion version) {
        WorkflowGraph graph = graphBuilder.buildGraph(version);
        WorkflowValidationResponse response = graphValidator.validate(graph);

        if (version.getNodes() != null) {
            for (WorkflowNode node : version.getNodes()) {
                nodeValidator.validateNode(node, response);
            }
        }

        if (version.getConnections() != null) {
            for (WorkflowConnection connection : version.getConnections()) {
                connectionValidator.validateConnection(connection, response);
            }
        }

        variableValidator.validateVariables(version.getVariables(), response);

        return response;
    }
}
