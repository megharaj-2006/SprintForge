package org.SprintForge.modules.workflow.secret;

import java.util.List;

public interface SecretService {
    WorkflowSecret createSecret(Long workspaceId, String name, String rawValue, String scope);
    List<WorkflowSecret> getSecrets(Long workspaceId);
    String getDecryptedSecret(Long workspaceId, String name);
    void deleteSecret(Long secretId);
}
