package org.SprintForge.modules.workflow.sdk.exception;

public class NodeException extends RuntimeException {
    public NodeException(String message) {
        super(message);
    }
    public NodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
