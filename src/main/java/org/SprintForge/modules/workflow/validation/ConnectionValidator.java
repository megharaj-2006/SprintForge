package org.SprintForge.modules.workflow.validation;

import org.SprintForge.modules.workflow.dto.WorkflowValidationResponse;
import org.SprintForge.modules.workflow.entity.WorkflowConnection;
import org.springframework.stereotype.Component;

@Component
public class ConnectionValidator {

    public void validateConnection(WorkflowConnection connection, WorkflowValidationResponse response) {
        if (connection.getSourceNode() == null) {
            response.addError("INVALID_CONNECTION_SOURCE", "Connection ID " + connection.getId() + " is missing a source node", String.valueOf(connection.getId()));
        }
        if (connection.getTargetNode() == null) {
            response.addError("INVALID_CONNECTION_TARGET", "Connection ID " + connection.getId() + " is missing a target node", String.valueOf(connection.getId()));
        }
        if (connection.getSourceNode() != null && connection.getTargetNode() != null &&
            connection.getSourceNode().getId().equals(connection.getTargetNode().getId())) {
            response.addError("SELF_REFERENCING_CONNECTION", "Connection ID " + connection.getId() + " connects a node to itself", String.valueOf(connection.getId()));
        }
    }
}
