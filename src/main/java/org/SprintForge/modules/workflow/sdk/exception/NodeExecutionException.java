package org.SprintForge.modules.workflow.sdk.exception;

public class NodeExecutionException extends NodeException {
    public NodeExecutionException(String message) {
        super(message);
    }
    public NodeExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
