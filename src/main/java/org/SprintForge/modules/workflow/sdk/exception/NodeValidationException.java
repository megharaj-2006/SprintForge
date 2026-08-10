package org.SprintForge.modules.workflow.sdk.exception;

import java.util.List;

public class NodeValidationException extends NodeException {
    private final List<String> errors;

    public NodeValidationException(String message, List<String> errors) {
        super(message + ": " + errors);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
