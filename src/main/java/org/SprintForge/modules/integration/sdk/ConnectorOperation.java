package org.SprintForge.modules.integration.sdk;

public interface ConnectorOperation {
    String getOperationKey();
    OperationDescriptor getDescriptor();
    boolean validate(OperationContext context);
    OperationResult execute(OperationContext context);
    default void rollback(OperationContext context) {}
    default void cleanup(OperationContext context) {}
}
